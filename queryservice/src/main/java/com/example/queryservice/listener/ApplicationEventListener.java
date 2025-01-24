package com.example.queryservice.listener;

import com.example.queryservice.events.Event;
import com.example.queryservice.model.Application;
import com.example.queryservice.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {

    private final ApplicationRepository applicationRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "application.queue")
    public void handleApplicationEvent(Event event) {
        String operation = event.getOperation().toLowerCase();
        log.info("Received event: {} from 'application.queue'", operation);

        try {
            switch (operation) {

                case "application.create": {
                    log.debug("Processing 'application.create' event");
                    Application application = objectMapper.convertValue(event.getPayload(), Application.class);
                    applicationRepository.save(application);
                    log.info("Successfully processed 'application.create' event for Application ID: {}", application.getId());
                    break;
                }

                case "application.delete": {
                    log.debug("Processing 'application.delete' event");
                    String idString = objectMapper.convertValue(event.getPayload(), String.class);
                    UUID applicationId = UUID.fromString(idString);
                    applicationRepository.deleteById(applicationId);
                    log.info("Successfully processed 'application.delete' event for Application ID: {}", applicationId);
                    break;
                }

                default:
                    log.warn("Received unknown operation: {}", event.getOperation());
                    throw new IllegalArgumentException("Unknown operation: " + event.getOperation());
            }
        } catch (IllegalArgumentException e) {
            log.error("Error processing event: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while processing event: {}", e.getMessage(), e);
            throw e;
        }
    }
}
