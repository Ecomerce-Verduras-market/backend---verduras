package com.verduritas.notification.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String COMMERCE_EXCHANGE = "commerce.events";
    public static final String PAYMENT_PROCESSED_QUEUE = "notification.payment-processed";
    public static final String STOCK_UPDATED_QUEUE = "notification.stock-updated";

    @Bean DirectExchange commerceExchange() { return new DirectExchange(COMMERCE_EXCHANGE); }
    @Bean Queue paymentProcessedQueue() { return new Queue(PAYMENT_PROCESSED_QUEUE, true); }
    @Bean Queue stockUpdatedQueue() { return new Queue(STOCK_UPDATED_QUEUE, true); }
    @Bean Binding paymentProcessedBinding(@Qualifier("paymentProcessedQueue") Queue paymentProcessedQueue,
                                          DirectExchange commerceExchange) {
        return BindingBuilder.bind(paymentProcessedQueue).to(commerceExchange).with("payment.processed");
    }
    @Bean Binding stockUpdatedBinding(@Qualifier("stockUpdatedQueue") Queue stockUpdatedQueue,
                                      DirectExchange commerceExchange) {
        return BindingBuilder.bind(stockUpdatedQueue).to(commerceExchange).with("stock.updated");
    }
    @Bean Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
