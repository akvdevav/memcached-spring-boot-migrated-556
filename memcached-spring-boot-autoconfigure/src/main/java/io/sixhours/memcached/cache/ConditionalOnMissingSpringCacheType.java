package io.sixhours.memcached.cache;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Conditional} that only matches when the {@code spring.cache.type} property is
 * not defined in the {@link Environment}.
 *
 * @author Sasa Bolic
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMissingSpringCacheType.class)
@interface ConditionalOnMissingSpringCacheType {

}