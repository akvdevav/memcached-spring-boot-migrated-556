package io.sixhours.memcached.cache;

/**
 * Memcached client interface.
 *
 * @author Igor Bolic
 */
public interface IMemcachedClient {

    Object nativeClient();

    Object get(String key);

    void set(String key, int exp, Object value);

    void touch(final String key, final int exp);

    void delete(String key);

    void flush();

    long incr(String key, int by);

    void shutdown();
}