package com.example.commandservice.service;

import com.example.commandservice.dto.RaceDTO;
import com.example.commandservice.events.EventPublisherService;
import com.example.commandservice.exception.RaceNotFoundException;
import com.example.commandservice.mapper.Mapper;
import com.example.commandservice.model.Race;
import com.example.commandservice.repository.ApplicationRepository;
import com.example.commandservice.repository.RaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RaceService {

    private final RaceRepository raceRepository;
    private final ApplicationRepository applicationRepository;
    private final EventPublisherService eventPublisherService;
    private final Mapper mapper;

    public RaceDTO createRace(RaceDTO raceDTO) {
        log.debug("Creating race with data: {}", raceDTO);
        Race race = mapper.toRaceEntity(raceDTO);
        Race savedRace = raceRepository.save(race);
        log.info("Race created with ID: {}", savedRace.getId());

        eventPublisherService.publishEvent("race.create", savedRace);
        log.debug("Published race.create event for race ID: {}", savedRace.getId());

        return mapper.toRaceDTO(savedRace);
    }

    public RaceDTO updateRace(UUID id, Map<String, Object> updates) {
        log.debug("Updating race with ID: {}. Updates: {}", id, updates);
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Race not found with ID: {}", id);
                    return new RaceNotFoundException("Race not found with ID: " + id);
                });

        updates.forEach((key, value) -> {
            try {
                Field field = Race.class.getDeclaredField(key);
                field.setAccessible(true);

                if (field.getType().equals(UUID.class)) {
                    field.set(race, UUID.fromString(value.toString()));
                } else if (field.getType().isEnum()) {
                    Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), value.toString().toUpperCase());
                    field.set(race, enumValue);
                } else {
                    field.set(race, value);
                }
                log.debug("Updated field '{}' to value '{}'", key, value);
            } catch (NoSuchFieldException e) {
                log.error("Invalid field: {}", key, e);
                throw new IllegalArgumentException("Invalid field: " + key, e);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                log.error("Error updating field: {}", key, e);
                throw new RuntimeException("Error updating field: " + key, e);
            }
        });

        Race updatedRace = raceRepository.save(race);
        log.info("Race with ID: {} updated successfully.", id);

        eventPublisherService.publishEvent("race.update", updatedRace);
        log.debug("Published race.update event for race ID: {}", updatedRace.getId());

        return mapper.toRaceDTO(updatedRace);
    }

    @Transactional
    public void deleteRace(UUID id) {
        log.debug("Deleting race with ID: {}", id);
        if (!raceRepository.existsById(id)) {
            log.error("Race not found with ID: {}", id);
            throw new RaceNotFoundException("Race not found with ID: " + id);
        }

        try {
            applicationRepository.deleteByRaceId(id);
            raceRepository.deleteById(id);
            log.info("Race with ID: {} deleted successfully.", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while deleting race with ID: {}", id, e);
            throw new RuntimeException("Cannot delete race due to data integrity violation", e);
        }

        eventPublisherService.publishEvent("race.delete", id);
        log.debug("Published race.delete event for race ID: {}", id);
    }
}
