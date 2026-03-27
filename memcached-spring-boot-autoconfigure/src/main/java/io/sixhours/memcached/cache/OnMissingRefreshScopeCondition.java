package io.sixhours.memcached.cache;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks if the {@link org.springframework.cloud.context.scope.refresh.RefreshScope} class is
 * not defined in the {@link org.springframework.core.env.Environment}.
 *
 * @author Igor Bolic
 */
public class OnMissingRefreshScopeCondition extends AnyNestedCondition {

    public OnMissingRefreshScopeCondition() {
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnMissingClass("org.springframework.cloud.context.scope.refresh.RefreshScope")
    static class MissingRefreshScope {
    }

    @ConditionalOnMissingBean(RefreshAutoConfiguration.class)
    static class MissingRefreshAutoConfiguration {
    }

}