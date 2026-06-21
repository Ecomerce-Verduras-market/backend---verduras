package com.verduritas.product.application;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank String sku,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @NotBlank @Size(max = 1024) String imageUrl
) {
}
