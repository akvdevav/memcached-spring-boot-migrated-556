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

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Factory for the {@link RedisCacheManager} instances.
 *
 * @author Igor Bolic
 * @author Sasa Bolic
 */
public abstract class MemcachedCacheManagerFactory {

    protected final MemcachedCacheProperties properties;

    protected MemcachedCacheManagerFactory(MemcachedCacheProperties properties) {
        this.properties = properties;
    }

    public RedisCacheManager create() throws IOException {
        final RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(java.time.Duration.ofSeconds(properties.getExpiration().getSeconds()));

        final RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory())
                .withInitialCacheConfigurations(
                        properties.getExpirationPerCache().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> RedisCacheConfiguration.defaultCacheConfig()
                                                .entryTtl(java.time.Duration.ofSeconds(e.getValue().getSeconds()))
                                ))
                )
                .withDisabledCacheNames(properties.getDisabledCacheNames())
                .withMetricsCacheNames(properties.getMetricsCacheNames())
                .withCacheDefaults(config)
                .build();

        return cacheManager;
    }

    abstract RedisConnectionFactory connectionFactory() throws IOException;
}