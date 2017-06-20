package wsale.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by john on 16/8/7.
 */
public class ExecutorCompletionServiceImpl<V> implements CompletionService<V> {
    private final Executor executor;
    private List<Future<V>> futureList;
    private Map<String,RunnableFuture<V>> cache;

    /**
     * FutureTask extension to enqueue upon completion
     */
    private class QueueingFuture extends FutureTask<V> {

        QueueingFuture(RunnableFuture<V> task) {

            super(task, null);
            this.task = task;

        }

        QueueingFuture(RunnableFuture nullTask,RunnableFuture<V> task) {

            super(nullTask, null);
            this.task = task;

        }

        private final Future<V> task;

        public V _get() throws InterruptedException, ExecutionException {
            V v = task.get();
            if (task instanceof WrapFutureTask) {
                ((WrapFutureTask) task).getTask().handle(v);
            }
            return v;
        }
    }

    private class WrapFutureTask<Void> extends FutureTask<Void> {
        private Task<?, Void> task;

        WrapFutureTask(Task<?, Void> task) {
            super(task);
            this.task = task;
        }

        public Task<?, Void> getTask() {
            return this.task;
        }
    }


    private RunnableFuture<V> newTaskFor(Task<?, V> task) {


        return new WrapFutureTask<V>(task);
    }

    private QueueingFuture newQueueingFuture(WrapFutureTask task) {
        CacheKey cacheKey = task.getTask().getCacheKey();
        if (cacheKey != null) {
            if (cache == null) {
                cache = new HashMap<String, RunnableFuture<V>>();
            }
            WrapFutureTask runnableFuture = (WrapFutureTask) cache.get(cacheKey.toString());
            if (runnableFuture == null) {
                cache.put(cacheKey.toString(), task);
            } else {
                return new QueueingFuture(newTaskFor(new Task() {
                    @Override
                    public Object call() throws Exception {
                        return null;
                    }
                }), new WrapFutureTask(task.getTask()) {
                    private WrapFutureTask wrapFutureTask;

                    public WrapFutureTask set(WrapFutureTask wrapFutureTask) {
                        this.wrapFutureTask = wrapFutureTask;
                        return this;
                    }

                    public Object get() throws InterruptedException, ExecutionException {
                        return wrapFutureTask.get();
                    }
                }.set(runnableFuture));
            }
        }
        return new QueueingFuture(task);
    }

    /**
     * Creates an ExecutorCompletionService using the supplied
     * executor for base task execution and a
     * {@link java.util.concurrent.LinkedBlockingQueue} as a completion queue.
     *
     * @param executor the executor to use
     * @throws NullPointerException if executor is {@code null}
     */
    public ExecutorCompletionServiceImpl(Executor executor) {
        if (executor == null)
            throw new NullPointerException();
        this.executor = executor;
        this.futureList = new ArrayList<Future<V>>();
    }


    public Future<V> submit(Task<?, V> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<V> f = newTaskFor(task);
        FutureTask queueingFuture = newQueueingFuture((WrapFutureTask)f);
        executor.execute(queueingFuture);
        futureList.add(queueingFuture);
        return f;
    }

    private void doGet(Future future){
        try {
            ((QueueingFuture) future)._get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void sync() {
        if (futureList != null) {
            while (futureList.size() > 0) {
                Future future = futureList.get(0);
                doGet(future);
                futureList.remove(future);
            }
        }
    }

}
