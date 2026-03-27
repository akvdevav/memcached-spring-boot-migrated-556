package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.Condition;
import org.springframework.core.env.Environment;

/**
 * {@link Condition} that checks that {@code spring.cache.type} property is
 * not defined in the {@link Environment}.
 *
 * @author Sasa Bolic
 */
class OnMissingSpringCacheType extends NoneNestedConditions {

    OnMissingSpringCacheType() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty("spring.cache.type")
    static class SpringCacheType {
    }
}