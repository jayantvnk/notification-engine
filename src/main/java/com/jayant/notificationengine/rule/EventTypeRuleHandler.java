package com.jayant.notificationengine.rule;

import com.jayant.notificationengine.delivery.ChannelType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Phase 1 rule: a simple, hardcoded eventType -> channel(s) mapping.
 * Deliberately dumb - this is the "prove the chain works" handler
 * before Phase 2 adds throttling/dedupe/user-preference handlers
 * alongside it.
 */
@Component
@Order(1) // Determines position in that auto-collected list.runs first, every time
public class EventTypeRuleHandler implements RuleHandler {

    @Override
    public EvaluationContext handle(EvaluationContext context) {
        String eventType = context.getEvent().eventType();

        return switch (eventType) {
            case "ORDER_SHIPPED" -> context.toBuilder()
                    .channels(Set.of(ChannelType.EMAIL, ChannelType.SSE))
                    .message("Your order has shipped!")
                    .build();

            case "PASSWORD_RESET" -> context.toBuilder()
                    .channels(Set.of(ChannelType.EMAIL))
                    .priority(Priority.HIGH)
                    .message("A password reset was requested for your account.")
                    .build();

            // Unknown event type: no channels decided, suppress -
            // no point handing this to delivery with nowhere to send it.
            default -> context.toBuilder()
                    .suppressed(true) // decide whether "suppress unknown events"
                    .build();
        };
    }
}
