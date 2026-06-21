package com.verduritas.shared.events;

import java.time.Instant;
import java.util.UUID;

public record StockUpdatedEvent(
        UUID eventId,
        UUID productId,
        int availableQuantity,
        String reason,
        Instant occurredAt
) {
}
