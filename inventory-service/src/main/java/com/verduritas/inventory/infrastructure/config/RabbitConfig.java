package com.verduritas.inventory.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String COMMERCE_EXCHANGE = "commerce.events";
    public static final String ORDER_CREATED_QUEUE = "inventory.order-created";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String STOCK_UPDATED_ROUTING_KEY = "stock.updated";

    @Bean DirectExchange commerceExchange() { return new DirectExchange(COMMERCE_EXCHANGE); }
    @Bean Queue orderCreatedQueue() { return new Queue(ORDER_CREATED_QUEUE, true); }
    @Bean Binding orderCreatedBinding(Queue orderCreatedQueue, DirectExchange commerceExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(commerceExchange).with(ORDER_CREATED_ROUTING_KEY);
    }
    @Bean Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }
}
