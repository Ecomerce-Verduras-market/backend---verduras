package com.verduritas.payment.application;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(UUID id, UUID orderId, BigDecimal amount, String status) {
}
