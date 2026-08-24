package com.jayant.notificationengine.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Represents "something happened" - the raw domain event that enters
 * the system via ingestion (REST for now, a broker later).
 * <p>
 * Immutable and transient: this is NOT a JPA entity. It lives only for
 * the duration of ingestion -> rule evaluation, then either gets dropped
 * (suppressed by the rule chain) or turned into a persisted {@code Notification}.
 */
public record NotificationEvent(
        UUID eventId,
        String eventType,
        String userId,
        Map<String, Object> payload,
        Instant timestamp
) {

    /**
     * Factory method used by the controller: generates eventId + timestamp
     * server-side so callers can't spoof either.
     */
    public static NotificationEvent from(String eventType, String userId, Map<String, Object> payload) {
        return new NotificationEvent(
                UUID.randomUUID(),
                eventType,
                userId,
                payload == null ? Map.of() : payload,
                Instant.now()
        );
    }
}
