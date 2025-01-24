package com.example.queryservice.listener;

import com.example.queryservice.events.Event;
import com.example.queryservice.model.Race;
import com.example.queryservice.repository.RaceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RaceEventListener {

    private final RaceRepository raceRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "race.queue")
    public void handleRaceEvent(Event event) {
        String operation = event.getOperation().toLowerCase();
        log.info("Received event: {} from 'race.queue'", operation);

        try {
            switch (operation) {

                case "race.create":
                case "race.update": {
                    log.debug("Processing '{}' event", event.getOperation());
                    Race race = objectMapper.convertValue(event.getPayload(), Race.class);
                    raceRepository.save(race);
                    log.info("Successfully processed '{}' event for Race ID: {}", event.getOperation(), race.getId());
                    break;
                }

                case "race.delete": {
                    log.debug("Processing 'race.delete' event");
                    String idString = objectMapper.convertValue(event.getPayload(), String.class);
                    UUID raceId = UUID.fromString(idString);
                    raceRepository.deleteById(raceId);
                    log.info("Successfully processed 'race.delete' event for Race ID: {}", raceId);
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
