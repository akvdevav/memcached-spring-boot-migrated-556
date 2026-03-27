package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the Memcached cache
 * backed by XMemcached client.
 * Creates {@link CacheManager} when caching is enabled via {@link EnableCaching}.
 *
 * @author Igor Bolic
 * @author Sasa Bolic
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({net.rubyeye.xmemcached.MemcachedClient.class, CacheManager.class})
@Conditional(NotAppEngineProviderCondition.class)
@EnableConfigurationProperties(MemcachedCacheProperties.class)
public class XMemcachedCacheAutoConfiguration {

    @Configuration
    @ConditionalOnRefreshScope
    static class RefreshableMemcachedCacheConfiguration {

        @Bean
        @RefreshScope
        @ConditionalOnMissingBean(value = MemcachedCacheManager.class, search = SearchStrategy.CURRENT)
        public MemcachedCacheManager cacheManager(MemcachedCacheProperties properties) throws IOException {
            return new XMemcachedCacheManagerFactory(properties).create();
        }
    }

    @Configuration
    @ConditionalOnMissingRefreshScope
    static class MemcachedCacheConfiguration {

        @Bean
        @ConditionalOnMissingBean(value = MemcachedCacheManager.class, search Strategy.CURRENT)
        public MemcachedCacheManager cacheManager(MemcachedCacheProperties properties) throws IOException {
            return new XMemcachedCacheManagerFactory(properties).create();
        }
    }
}