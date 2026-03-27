package io.sixhours.memcached.cache;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Conditional} that only matches when the {@link org.springframework.cloud.context.scope.refresh.RefreshScope} class is
 * not defined in the {@link org.springframework.core.env.Environment}.
 *
 * @author Igor Bolic
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMissingRefreshScopeCondition.class)
@interface ConditionalOnMissingRefreshScope {
}