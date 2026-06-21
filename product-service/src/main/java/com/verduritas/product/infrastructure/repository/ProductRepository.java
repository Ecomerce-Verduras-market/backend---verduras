package com.verduritas.product.infrastructure.repository;

import com.verduritas.product.infrastructure.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    boolean existsBySku(String sku);
}
