package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the Memcached cache.
 * Creates {@link CacheManager} when caching is enabled via {@link EnableCaching}.
 *
 * @author Igor Bolic
 * @author Sasa Bolic
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingSpringCacheType
@ConditionalOnBean(CacheAspectSupport.class)
@ConditionalOnMissingBean({CacheManager.class, CacheResolver.class})
@EnableConfigurationProperties(MemcachedCacheProperties.class)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@AutoConfigureAfter(name = "org.springframework.cloud.autoconfigure.RefreshAutoConfiguration")
@Import({AppEngineMemcachedCacheAutoConfiguration.class, XMemcachedCacheAutoConfiguration.class, SpyMemcachedCacheAutoConfiguration.class})
public class MemcachedCacheAutoConfiguration {
}