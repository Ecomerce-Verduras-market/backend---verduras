package com.verduritas.order.infrastructure.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String COMMERCE_EXCHANGE = "commerce.events";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    @Bean
    DirectExchange commerceExchange() {
        return new DirectExchange(COMMERCE_EXCHANGE);
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
