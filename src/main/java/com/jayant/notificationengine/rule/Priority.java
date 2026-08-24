package com.jayant.notificationengine.rule;

/**
 * Not meaningfully used until Phase 2's richer rules (throttling,
 * user-preference-aware ordering, etc.) - defined now so the
 * EvaluationContext contract doesn't need to change shape later.
 */
public enum Priority {
    LOW,
    NORMAL,
    HIGH
}
