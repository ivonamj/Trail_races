package com.example.queryservice.events;

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

    public void publishResult(String status, String message) {
        String exchange = "result.topic";
        String routingKey = status.toLowerCase();

        log.info("Publishing result event to exchange '{}' with routing key '{}'", exchange, routingKey);

        log.debug("Event message: {}", message);

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Successfully published result event with routing key '{}'", routingKey);
        } catch (Exception e) {
            log.error("Failed to publish result event to exchange '{}' with routing key '{}'", exchange, routingKey, e);
            throw e;
        }
    }
}
