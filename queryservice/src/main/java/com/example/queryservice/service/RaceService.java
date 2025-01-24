package com.example.queryservice.service;

import com.example.queryservice.dto.RaceDTO;
import com.example.queryservice.mapper.Mapper;
import com.example.queryservice.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RaceService {

    private final RaceRepository raceRepository;
    private final Mapper mapper;

    public List<RaceDTO> getAllRaces() {
        log.info("Fetching all races");
        List<RaceDTO> races = raceRepository.findAll().stream()
                .map(mapper::toRaceDTO)
                .toList();
        log.info("Successfully fetched {} races", races.size());
        return races;
    }

    public Optional<RaceDTO> getRaceById(UUID id) {
        log.info("Fetching race with id: {}", id);
        Optional<RaceDTO> race = raceRepository.findById(id)
                .map(mapper::toRaceDTO);
        if (race.isPresent()) {
            log.info("Successfully fetched race with id: {}", id);
        } else {
            log.warn("Race not found with id: {}", id);
        }
        return race;
    }
}
