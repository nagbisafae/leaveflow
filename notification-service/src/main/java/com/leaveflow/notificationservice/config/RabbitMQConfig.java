package com.leaveflow.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Noms des queues
    public static final String LEAVE_EVENTS_QUEUE = "leave.events.queue";
    public static final String LEAVE_EVENTS_EXCHANGE = "leave.events.exchange";
    public static final String LEAVE_EVENTS_ROUTING_KEY = "leave.events";

    // Créer la queue
    @Bean
    public Queue leaveEventsQueue() {
        return new Queue(LEAVE_EVENTS_QUEUE, true);  // durable = true
    }

    // Créer l'exchange
    @Bean
    public DirectExchange leaveEventsExchange() {
        return new DirectExchange(LEAVE_EVENTS_EXCHANGE);
    }

    // Binding queue à l'exchange
    @Bean
    public Binding binding(Queue leaveEventsQueue, DirectExchange leaveEventsExchange) {
        return BindingBuilder
                .bind(leaveEventsQueue)
                .to(leaveEventsExchange)
                .with(LEAVE_EVENTS_ROUTING_KEY);
    }

    // RabbitTemplate - Spring Boot configurera automatiquement le JSON converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}