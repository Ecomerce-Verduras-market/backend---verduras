package com.verduritas.product.application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, String name, String sku, BigDecimal price, String imageUrl, boolean active) implements Serializable {
}
