package io.sixhours.memcached.cache;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the Memcached {@link CacheMeterBinderProvider} bean.
 *
 * @author Igor Bolic
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(MemcachedCacheManager.class)
@ConditionalOnClass(MeterBinder.class)
public class MemcachedCacheMeterBinderProviderConfiguration {

    @Bean
    public MemcachedCacheMeterBinderProvider memcachedCacheMeterBinderProvider() {
        return new MemcachedCacheMeterBinderProvider();
    }
}