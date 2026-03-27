/*
 * Copyright 2016-2026 Sixhours
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sixhours.memcached.cache;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for the Valkey {@link RedisCacheManager} instances.
 *
 * @author Igor Bolic
 * @author Sasa Bolic
 */
public class XMemcachedCacheManagerFactory extends MemcachedCacheManagerFactory {

    public XMemcachedCacheManagerFactory(MemcachedCacheProperties properties) {
        super(properties);
    }

    @Override
    IMemcachedClient memcachedClient() throws IOException {
        // This implementation is replaced with Redis-based caching
        // The original memcached client creation is removed and replaced with Redis configuration
        throw new UnsupportedOperationException("Memcached client is deprecated. Use Redis-based caching instead.");
    }

    // Placeholder for Redis-based implementation
    // This would typically involve creating a RedisCacheManager using RedisConnectionFactory
    // and setting up appropriate serializers for caching operations
    protected RedisCacheManager createRedisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .withDefaultCacheConfig()
                .build();
    }

    // Placeholder for Redis template configuration
    protected RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer());
        return template;
    }
}