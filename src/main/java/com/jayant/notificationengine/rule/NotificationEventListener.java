package com.jayant.notificationengine.rule;

import com.jayant.notificationengine.event.NotificationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Subscriber side of the Pub/Sub seam: reacts to whatever
 * EventPublisher publishes. EventController/EventPublisher have no
 * idea this class exists - that's the whole point of decoupling
 * ingestion from evaluation.
 * <p>
 * @Async is what actually makes this non-blocking: without it,
 * @EventListener methods run synchronously on the publishing thread,
 * meaning POST /api/events would wait for rule evaluation (and,
 * later, delivery) to finish before responding.
 * <p>
 * TODO: once DeliveryDispatcher exists, hand the final EvaluationContext
 * to it here instead of just logging.
 */
@Component
public class NotificationEventListener {

    private final RuleChainConfig ruleChain;

    public NotificationEventListener(RuleChainConfig ruleChain) {
        this.ruleChain = ruleChain;
    }

    @Async
    @EventListener
    public void onNotificationEvent(NotificationEvent event) {
        EvaluationContext initial = EvaluationContext.initial(event);
        EvaluationContext result = ruleChain.evaluate(initial);

        // Temporary - replaced by DeliveryDispatcher.dispatch(result) next.
        System.out.println("Evaluated event " + event.eventId() + " -> " + result);
    }
}
