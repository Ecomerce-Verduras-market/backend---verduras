package com.verduritas.payment.application;

import com.verduritas.payment.domain.PaymentStatus;
import com.verduritas.payment.infrastructure.config.RabbitConfig;
import com.verduritas.payment.infrastructure.entity.PaymentEntity;
import com.verduritas.payment.infrastructure.entity.ProcessedEventEntity;
import com.verduritas.payment.infrastructure.repository.PaymentRepository;
import com.verduritas.payment.infrastructure.repository.ProcessedEventRepository;
import com.verduritas.shared.events.OrderCreatedEvent;
import com.verduritas.shared.events.PaymentProcessedEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final RabbitTemplate rabbitTemplate;

    public PaymentService(PaymentRepository paymentRepository, ProcessedEventRepository processedEventRepository, RabbitTemplate rabbitTemplate) {
        this.paymentRepository = paymentRepository;
        this.processedEventRepository = processedEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void process(OrderCreatedEvent event) {
        if (processedEventRepository.existsById(event.eventId())) return;
        var payment = paymentRepository.findByOrderId(event.orderId()).orElseGet(PaymentEntity::new);
        payment.setOrderId(event.orderId());
        payment.setAmount(event.amount());
        payment.setStatus(PaymentStatus.APPROVED);
        var saved = paymentRepository.save(payment);
        processedEventRepository.save(new ProcessedEventEntity(event.eventId()));
        var processed = new PaymentProcessedEvent(UUID.randomUUID(), saved.getId(), saved.getOrderId(), saved.getAmount(), saved.getStatus().name(), Instant.now());
        rabbitTemplate.convertAndSend(RabbitConfig.COMMERCE_EXCHANGE, RabbitConfig.PAYMENT_PROCESSED_ROUTING_KEY, processed);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map(payment -> new PaymentResponse(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getStatus().name()))
                .toList();
    }
}
