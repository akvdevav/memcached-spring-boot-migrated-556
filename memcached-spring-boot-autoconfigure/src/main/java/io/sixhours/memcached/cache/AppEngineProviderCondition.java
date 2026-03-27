package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks that {@code memcached.cache.provider} property is {@code appegnine}.
 *
 * @author Igor Bolic
 */
public class AppEngineProviderCondition extends AllNestedConditions  {

    public AppEngineProviderCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "memcached.cache", name = "provider", havingValue = "appengine")
    static class AppEngineCondition {
    }
}