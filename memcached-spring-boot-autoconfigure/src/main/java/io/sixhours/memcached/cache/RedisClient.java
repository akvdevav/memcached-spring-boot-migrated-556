package io.sixhours.memcached.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * {@code Redis} cache client implementation.
 *
 * @author Igor Bolic
 */
@Component
public class RedisClient implements IMemcachedClient {
    private static final String DEFAULT_KEY_PREFIX = "cache:";
    private static final long DEFAULT_EXPIRE_TIME = 3600L;

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisClient(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public void set(String key, int exp, Object value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public void touch(String key, int exp) {
        // In Redis, touch is handled by setting expiration time
        stringRedisTemplate.expire(key, exp, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void flush() {
        stringRedisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public long incr(String key, int by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    @Override
    public void shutdown() {
        // No explicit shutdown needed for RedisTemplate
    }

    @Override
    public Object nativeClient() {
        return redisTemplate;
    }
}