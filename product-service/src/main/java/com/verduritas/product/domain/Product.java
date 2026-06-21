package com.verduritas.product.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, String name, String sku, BigDecimal price, boolean active) {
}
