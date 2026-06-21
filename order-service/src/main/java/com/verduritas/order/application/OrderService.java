package com.verduritas.order.application;

import com.verduritas.order.infrastructure.config.RabbitConfig;
import com.verduritas.order.infrastructure.entity.OrderEntity;
import com.verduritas.order.infrastructure.repository.OrderRepository;
import com.verduritas.shared.events.OrderCreatedEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        var order = new OrderEntity();
        order.setUserId(request.userId());
        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setAmount(request.amount());
        var saved = repository.save(order);
        var event = new OrderCreatedEvent(UUID.randomUUID(), saved.getId(), saved.getUserId(), saved.getProductId(), saved.getQuantity(), saved.getAmount(), Instant.now());
        rabbitTemplate.convertAndSend(RabbitConfig.COMMERCE_EXCHANGE, RabbitConfig.ORDER_CREATED_ROUTING_KEY, event);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(OrderEntity entity) {
        return new OrderResponse(entity.getId(), entity.getUserId(), entity.getProductId(), entity.getQuantity(), entity.getAmount(), entity.getStatus().name());
    }
}
