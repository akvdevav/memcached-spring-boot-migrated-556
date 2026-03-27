package io.sixhours.memcached.cache;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Factory for the {@link MemcachedCacheManager} instances.
 *
 * @author Igor Bolic
 */
@Configuration
@EnableCaching
public class AppEngineMemcachedCacheManagerFactory extends MemcachedCacheManagerFactory {

    private final MemcachedCacheProperties properties;

    public AppEngineMemcachedCacheManagerFactory(MemcachedCacheProperties properties) {
        this.properties = properties;
    }

    @Override
    IMemcachedClient memcachedClient() {
        return new AppEngineMemcachedClient(MemcacheServiceFactory.getMemcacheService());
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(StringRedisSerializer.UTF_8)
                .serializeValuesWith(GenericJackson2JsonRedisSerializer.UTF_8);

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("default", config)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        template.setValueSerializer(GenericJackson2JsonRedisSerializer.UTF_8);
        return template;
    }
}