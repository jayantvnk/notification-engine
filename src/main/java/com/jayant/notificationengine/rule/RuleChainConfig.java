package com.jayant.notificationengine.rule;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Folds an ordered List<RuleHandler> over a starting EvaluationContext.
 * Spring injects every RuleHandler bean here already ordered by
 * @Order (see EventTypeRuleHandler) - this class doesn't know or care
 * how many handlers exist, only that it applies them in sequence.
 * <p>
 * Because each handler returns a NEW context instead of mutating,
 * this is a plain left-fold - no synchronization concerns even
 * though evaluate() may run on an async listener thread.
 */
@Component
public class RuleChainConfig { //RuleEvaluator

    private final List<RuleHandler> handlers;

    public RuleChainConfig(List<RuleHandler> handlers) {
        this.handlers = handlers;
    }

    public EvaluationContext evaluate(EvaluationContext initialContext) {
        EvaluationContext context = initialContext;
        for (RuleHandler handler : handlers) {
            context = handler.handle(context);
        }
        return context;
    }
    //early-exit on suppressed in future
}
