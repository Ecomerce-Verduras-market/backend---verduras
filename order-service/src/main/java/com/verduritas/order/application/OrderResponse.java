package com.verduritas.order.application;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(UUID id, UUID userId, UUID productId, int quantity, BigDecimal amount, String status) {
}
