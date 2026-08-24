package com.jayant.notificationengine.rule;

import com.jayant.notificationengine.delivery.ChannelType;
import com.jayant.notificationengine.event.NotificationEvent;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

/**
 * Immutable value object threaded through the Chain of Responsibility.
 * Each RuleHandler receives one instance and returns a NEW instance
 * (via toBuilder()) rather than mutating this one - see RuleHandler
 * for why.
 * <p>
 * @Value makes every field final and generates equals/hashCode/toString.
 * @Builder + toBuilder=true gives handlers a cheap way to say
 * "same as before, but with X changed" without a giant constructor call.
 */
@Value
@Builder(toBuilder = true) // copy with one field changed.
public class EvaluationContext {

    NotificationEvent event;
    Set<ChannelType> channels;
    Priority priority;
    boolean suppressed;
    String message;

    /**
     * Starting point for a fresh event entering the chain: no channels
     * decided yet, NORMAL priority by default, not suppressed, no
     * message formatted yet.
     */
    public static EvaluationContext initial(NotificationEvent event) {
        return EvaluationContext.builder()
                .event(event)
                .channels(Set.of())
                .priority(Priority.NORMAL)
                .suppressed(false)
                .message(null)
                .build();
    }
}
