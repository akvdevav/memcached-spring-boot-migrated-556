package io.sixhours.memcached.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * {@code Redis} memcached client implementation.
 *
 * @author Igor Bolic
 */
@Component
public class RedisMemcachedClient implements IMemcachedClient {

    private static final String CACHE_PREFIX = "memcached:";
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;

    public RedisMemcachedClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    @Override
    public RedisTemplate<String, Object> nativeClient() {
        return this.redisTemplate;
    }

    @Override
    public Object get(String key) {
        return this.valueOps.get(CACHE_PREFIX + key);
    }

    @Override
    public void set(String key, int exp, Object value) {
        this.valueOps.set(CACHE_PREFIX + key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void touch(String key, int exp) {
        Object value = this.valueOps.get(CACHE_PREFIX + key);
        if (value != null) {
            this.valueOps.set(CACHE_PREFIX + key, value, exp, TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(String key) {
        this.redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void flush() {
        // Redis does not support flushing all keys in a single operation
        // This implementation assumes a specific prefix pattern for cache keys
        // In practice, you might want to use Redis SCAN command or key patterns
        // For now, we'll leave this as a placeholder
    }

    @Override
    public long incr(String key, int by) {
        String cacheKey = CACHE_PREFIX + key;
        Long currentValue = (Long) this.valueOps.get(cacheKey);
        if (currentValue == null) {
            currentValue = 0L;
        }
        long newValue = currentValue + by;
        this.valueOps.set(cacheKey, newValue);
        return newValue;
    }

    @Override
    public void shutdown() {
        // Redis template handles connection management
    }
}