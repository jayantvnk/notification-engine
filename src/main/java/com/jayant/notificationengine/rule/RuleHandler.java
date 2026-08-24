package com.jayant.notificationengine.rule;

/**
 * Contract for one link in the Chain of Responsibility.
 * <p>
 * Deliberately takes and returns EvaluationContext (not void, not
 * mutating in place): each handler is a pure function. This is what
 * makes handlers trivially unit-testable in isolation and gives you
 * free tracing - log the context before/after any handler runs and
 * you know exactly what it changed, since nothing else could have.
 */
public interface RuleHandler {
    EvaluationContext handle(EvaluationContext context);
}
