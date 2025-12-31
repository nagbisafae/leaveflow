package com.leaveflow.notificationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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

    // NEW: ClassMapper to trust all packages
    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");  // Trust all packages
        return classMapper;
    }

    // NEW: Custom JSON converter with LocalDate support
    @Bean
    public MessageConverter jsonMessageConverter() {
        //Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        // Configure ObjectMapper for LocalDate
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Handle LocalDate
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*"); // ideally restrict in prod

        // ✅ map producer type id -> consumer class
        typeMapper.setIdClassMapping(Map.of(
                "com.leaveflow.leaveservice.dto.LeaveEventDto",
                com.leaveflow.notificationservice.dto.LeaveEventDto.class
        ));

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    // UPDATED: RabbitTemplate with custom converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());  // Use custom converter
        return rabbitTemplate;
    }
}