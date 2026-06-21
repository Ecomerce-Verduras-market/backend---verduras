package com.verduritas.inventory.application;

import com.verduritas.inventory.infrastructure.config.RabbitConfig;
import com.verduritas.inventory.infrastructure.entity.InventoryItemEntity;
import com.verduritas.inventory.infrastructure.entity.ProcessedEventEntity;
import com.verduritas.inventory.infrastructure.repository.InventoryRepository;
import com.verduritas.inventory.infrastructure.repository.ProcessedEventRepository;
import com.verduritas.shared.events.OrderCreatedEvent;
import com.verduritas.shared.events.StockUpdatedEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final RabbitTemplate rabbitTemplate;

    public InventoryService(InventoryRepository inventoryRepository, ProcessedEventRepository processedEventRepository, RabbitTemplate rabbitTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.processedEventRepository = processedEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public InventoryResponse upsert(UpsertStockRequest request) {
        var item = inventoryRepository.findById(request.productId()).orElseGet(InventoryItemEntity::new);
        item.setProductId(request.productId());
        item.setQuantity(request.quantity());
        item.setUpdatedAt(Instant.now());
        return toResponse(inventoryRepository.save(item));
    }

    @Transactional
    public void reserve(OrderCreatedEvent event) {
        if (processedEventRepository.existsById(event.eventId())) return;
        var item = inventoryRepository.findById(event.productId()).orElseGet(() -> {
            var created = new InventoryItemEntity();
            created.setProductId(event.productId());
            created.setQuantity(0);
            return created;
        });
        item.setQuantity(Math.max(0, item.getQuantity() - event.quantity()));
        item.setUpdatedAt(Instant.now());
        var saved = inventoryRepository.save(item);
        processedEventRepository.save(new ProcessedEventEntity(event.eventId()));
        rabbitTemplate.convertAndSend(RabbitConfig.COMMERCE_EXCHANGE, RabbitConfig.STOCK_UPDATED_ROUTING_KEY,
                new StockUpdatedEvent(UUID.randomUUID(), saved.getProductId(), saved.getQuantity(), "ORDER_RESERVED", Instant.now()));
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> findAll() {
        return inventoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    private InventoryResponse toResponse(InventoryItemEntity entity) {
        return new InventoryResponse(entity.getProductId(), entity.getQuantity(), entity.getUpdatedAt());
    }
}
