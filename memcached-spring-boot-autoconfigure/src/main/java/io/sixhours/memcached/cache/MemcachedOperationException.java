package io.sixhours.memcached.cache;

/**
 * Memcached operation exception.
 *
 * @author Igor Bolic
 */
public class MemcachedOperationException extends RuntimeException {

    public MemcachedOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}