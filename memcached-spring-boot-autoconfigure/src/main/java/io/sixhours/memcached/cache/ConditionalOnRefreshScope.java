package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Conditional} that only matches when the {@link org.springframework.cloud.context.scope.refresh.RefreshScope} class is
 * defined in the {@link org.springframework.core.env.Environment}.
 *
 * @author Igor Bolic
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnClass(RefreshScope.class)
@ConditionalOnBean(RefreshAutoConfiguration.class)
@interface ConditionalOnRefreshScope {
}