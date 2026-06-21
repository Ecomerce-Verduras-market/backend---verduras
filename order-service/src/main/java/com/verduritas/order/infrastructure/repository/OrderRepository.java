package com.verduritas.order.infrastructure.repository;

import com.verduritas.order.infrastructure.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
