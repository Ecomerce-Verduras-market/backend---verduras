package com.verduritas.inventory.application;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpsertStockRequest(@NotNull UUID productId, @Min(0) int quantity) {
}
