package com.verduritas.payment.infrastructure.messaging;

import com.verduritas.payment.application.PaymentService;
import com.verduritas.payment.infrastructure.config.RabbitConfig;
import com.verduritas.shared.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private final PaymentService paymentService;

    public OrderCreatedListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitConfig.ORDER_CREATED_QUEUE)
    void onOrderCreated(OrderCreatedEvent event) {
        paymentService.process(event);
    }
}
