package com.example.commandservice.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEvent(String routingKey, Object payload) {
        log.info("Publishing event with routing key: {}", routingKey);

        log.debug("Event payload: {}", payload);

        try {
            Event event = new Event(routingKey, payload);
            rabbitTemplate.convertAndSend("operation.topic", routingKey, event);
            log.info("Event published successfully to exchange 'operation.topic' with routing key: {}", routingKey);
        } catch (Exception e) {
            log.error("Failed to publish event with routing key: {}", routingKey, e);
            throw e;
        }
    }
}
