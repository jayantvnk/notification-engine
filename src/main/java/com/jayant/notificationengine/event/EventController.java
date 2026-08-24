package com.jayant.notificationengine.event;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * Ingestion entry point. Accepts a raw event over REST, builds the
 * internal NotificationEvent, and publishes it - then returns
 * immediately. It does NOT know or care what happens after publish();
 * rule evaluation and delivery both happen asynchronously off this
 * request thread.
 */
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventPublisher eventPublisher;

    public EventController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> ingest(@Valid @RequestBody EventIngestRequest request) {
        NotificationEvent event = NotificationEvent.from(
                request.eventType(),
                request.userId(),
                request.payload()
        );

        eventPublisher.publish(event);

        // 202 Accepted, not 200/201: we've accepted the event for processing,
        // we haven't guaranteed a notification was created or delivered yet
        // (the rule chain might suppress it entirely).
        return ResponseEntity.accepted().body(Map.of("eventId", event.eventId()));
    }
}
