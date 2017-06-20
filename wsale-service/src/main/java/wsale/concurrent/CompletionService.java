package wsale.concurrent;

import java.util.concurrent.Future;

/**
 * Created by john on 16/8/7.
 */
public interface CompletionService<V> {

    Future<V> submit(Task<?, V> task);

    void sync();
}
