package com.verduritas.inventory.domain;

import java.util.UUID;

public record InventoryItem(UUID productId, int quantity) {
}
