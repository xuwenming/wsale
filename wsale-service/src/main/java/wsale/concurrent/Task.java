package wsale.concurrent;

/**
 * Created by john on 16/8/7.
 */

import java.util.concurrent.Callable;

public abstract class Task<D, V> implements Callable<V> {


    private D d;
    private CacheKey cacheKey;
    Task() {

    }

    public Task(D d) {
        this.d = d;
    }


    public Task(CacheKey key,D d) {
        this.cacheKey = key;
        this.d = d;
    }

    public CacheKey getCacheKey() {
        return cacheKey;
    }

    public void handle(V v) {
        if (d != null)
            set(d, v);
    }

    protected void set(D d, V v) {

    }

    public D getD() {
        return d;
    }

}
