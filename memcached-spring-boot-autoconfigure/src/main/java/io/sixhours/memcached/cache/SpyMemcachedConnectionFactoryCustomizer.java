package io.sixhours.memcached.cache;

import net.spy.memcached.ConnectionFactoryBuilder;

public interface SpyMemcachedConnectionFactoryCustomizer {
    void customize(ConnectionFactoryBuilder builder);
}