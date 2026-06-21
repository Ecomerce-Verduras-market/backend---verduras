package com.verduritas.inventory.infrastructure.messaging;

import com.verduritas.inventory.application.InventoryService;
import com.verduritas.inventory.infrastructure.config.RabbitConfig;
import com.verduritas.shared.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private final InventoryService inventoryService;

    public OrderCreatedListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = RabbitConfig.ORDER_CREATED_QUEUE)
    void onOrderCreated(OrderCreatedEvent event) {
        inventoryService.reserve(event);
    }
}
