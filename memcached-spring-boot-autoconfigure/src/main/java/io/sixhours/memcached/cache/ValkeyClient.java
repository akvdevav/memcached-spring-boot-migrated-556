package io.sixhours.memcached.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * {@code Valkey} memcached client implementation.
 *
 * @author Sasa Bolic
 */
@Component
public class ValkeyClient implements IMemcachedClient {

    private final RedisTemplate<String, Object> redisTemplate;

    public ValkeyClient(RedisTemplate<String, Object> redisTemplate) {
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
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(key);
        if (value != null) {
            operations.set(key, value, exp, TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void flush() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public long incr(String key, int by) {
        return redisTemplate.opsForValue().increment(key, by).longValue();
    }

    @Override
    public void shutdown() {
        // Redis template handles connection lifecycle automatically
    }

    @Override
    public Object nativeClient() {
        return redisTemplate;
    }
}