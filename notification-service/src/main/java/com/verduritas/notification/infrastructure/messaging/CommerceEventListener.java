package com.verduritas.notification.infrastructure.messaging;

import com.verduritas.notification.application.NotificationService;
import com.verduritas.notification.infrastructure.config.RabbitConfig;
import com.verduritas.shared.events.PaymentProcessedEvent;
import com.verduritas.shared.events.StockUpdatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CommerceEventListener {
    private final NotificationService notificationService;

    public CommerceEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitConfig.PAYMENT_PROCESSED_QUEUE)
    void onPaymentProcessed(PaymentProcessedEvent event) {
        notificationService.paymentProcessed(event);
    }

    @RabbitListener(queues = RabbitConfig.STOCK_UPDATED_QUEUE)
    void onStockUpdated(StockUpdatedEvent event) {
        notificationService.stockUpdated(event);
    }
}
