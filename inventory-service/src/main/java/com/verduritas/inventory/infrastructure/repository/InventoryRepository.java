package com.verduritas.inventory.infrastructure.repository;

import com.verduritas.inventory.infrastructure.entity.InventoryItemEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItemEntity, UUID> {
}
