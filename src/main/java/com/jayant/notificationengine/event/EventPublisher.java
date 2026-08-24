package com.jayant.notificationengine.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Thin wrapper around Spring's ApplicationEventPublisher.
 * <p>
 * This is the Pub/Sub seam: EventController (the producer) only knows
 * about this class, not about who's listening downstream. Today that's
 * the rule chain (via @EventListener); tomorrow it could be several
 * independent listeners (analytics, audit log, notifications) without
 * this class or the controller ever changing.
 * <p>
 * Wrapping Spring's publisher (instead of injecting it directly
 * everywhere) also gives you one seam to swap for a real broker
 * (Kafka/RabbitMQ) in Phase 4 without touching the controller.
 */
@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(NotificationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
