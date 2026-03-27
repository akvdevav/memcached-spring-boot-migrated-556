package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks that {@code memcached.cache.provider} property is not {@code appegnine}.
 *
 * @author Igor Bolic
 */
public class NotAppEngineProviderCondition extends NoneNestedConditions {

    public NotAppEngineProviderCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "memcached.cache", name = "provider", havingValue = "appengine")
    static class AppEngineCondition {
    }
}