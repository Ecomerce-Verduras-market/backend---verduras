package com.verduritas.inventory.infrastructure.repository;

import com.verduritas.inventory.infrastructure.entity.ProcessedEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {
}
