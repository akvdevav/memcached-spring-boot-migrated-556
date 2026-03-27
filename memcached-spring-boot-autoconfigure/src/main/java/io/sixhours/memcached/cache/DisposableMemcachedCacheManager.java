package io.sixhours.memcached.cache;

import org.springframework.beans.factory.DisposableBean;

/**
 * Disposable {@link MemcachedCacheManager} bean.
 *
 * @author Igor Bolic
 */
class DisposableMemcachedCacheManager extends MemcachedCacheManager implements DisposableBean {

    public DisposableMemcachedCacheManager(IMemcachedClient memcachedClient) {
        super(memcachedClient);
    }

    @Override
    public void destroy() {
        this.memcachedClient.shutdown();
    }
}