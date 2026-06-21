package com.verduritas.notification.application;

import com.verduritas.shared.events.PaymentProcessedEvent;
import com.verduritas.shared.events.StockUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void paymentProcessed(PaymentProcessedEvent event) {
        log.info("notification_type=PAYMENT_PROCESSED order_id={} payment_id={} status={} amount={}",
                event.orderId(), event.paymentId(), event.status(), event.amount());
    }

    public void stockUpdated(StockUpdatedEvent event) {
        log.info("notification_type=STOCK_UPDATED product_id={} available_quantity={} reason={}",
                event.productId(), event.availableQuantity(), event.reason());
    }
}
