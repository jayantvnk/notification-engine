package com.jayant.notificationengine.event;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * REST-facing request DTO for POST /api/events.
 * <p>
 * Kept separate from {@link NotificationEvent} on purpose: validation
 * annotations belong at the ingestion boundary, not on the internal
 * domain object. If a future ingestion source (e.g. a Kafka consumer)
 * builds NotificationEvent differently, this DTO doesn't need to change
 * and vice versa.
 */
public record EventIngestRequest(
        @NotBlank(message = "eventType is required")
        String eventType,

        @NotBlank(message = "userId is required")
        String userId,

        Map<String, Object> payload
) {
}
