package com.verduritas.inventory.application;

import java.time.Instant;
import java.util.UUID;

public record InventoryResponse(UUID productId, int quantity, Instant updatedAt) {
}
