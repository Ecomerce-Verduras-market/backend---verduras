package com.verduritas.order.application;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID userId,
        @NotNull UUID productId,
        @Min(1) int quantity,
        @NotNull @DecimalMin("0.01") BigDecimal amount
) {
}
