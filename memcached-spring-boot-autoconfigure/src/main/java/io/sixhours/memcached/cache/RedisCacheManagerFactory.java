package io.sixhours.memcached.cache;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.Builder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

/**
 * Factory for the Redis {@link RedisCacheManager} instances.
 *
 * @author Sasa Bolic
 */
public class RedisCacheManagerFactory {

    private final RedisConnectionFactory redisConnectionFactory;
    private final StringRedisTemplate stringRedisTemplate;
    private final Map<String, Duration> cacheExpiration;

    public RedisCacheManagerFactory(RedisConnectionFactory redisConnectionFactory,
                                    StringRedisTemplate stringRedisTemplate,
                                    Map<String, Duration> cacheExpiration) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.stringRedisTemplate = stringRedisTemplate;
        this.cacheExpiration = cacheExpiration;
    }

    public RedisCacheManager redisCacheManager() {
        Builder builder = RedisCacheManager.builder(redisConnectionFactory)
                .withSerializationContext(RedisSerializationContext.<String, Object>newSerializationContext(new StringRedisSerializer())
                        .value(new GenericJackson2JsonRedisSerializer())
                        .build());

        if (cacheExpiration != null && !cacheExpiration.isEmpty()) {
            builder.withInitialCacheConfigurations(cacheExpiration);
        }

        return builder.build();
    }
}