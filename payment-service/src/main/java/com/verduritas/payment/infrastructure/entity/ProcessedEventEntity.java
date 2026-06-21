package com.verduritas.payment.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processed_events")
public class ProcessedEventEntity {
    @Id
    private UUID eventId;
    private Instant processedAt = Instant.now();

    public ProcessedEventEntity() {
    }

    public ProcessedEventEntity(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getEventId() { return eventId; }
    public Instant getProcessedAt() { return processedAt; }
}
