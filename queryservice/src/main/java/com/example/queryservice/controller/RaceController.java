package com.example.queryservice.controller;

import com.example.queryservice.dto.RaceDTO;
import com.example.queryservice.exception.RaceNotFoundException;
import com.example.queryservice.service.RaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/races")
@RequiredArgsConstructor
@Slf4j
public class RaceController {

    private final RaceService raceService;

    @GetMapping
    public ResponseEntity<List<RaceDTO>> getAllRaces() {
        log.info("Received request to get all races");
        List<RaceDTO> races = raceService.getAllRaces();
        log.info("Successfully retrieved {} races", races.size());
        return ResponseEntity.ok(races);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDTO> getRaceById(@PathVariable UUID id) {
        log.info("Received request to get race with id: {}", id);
        RaceDTO race = raceService.getRaceById(id)
                .orElseThrow(() -> new RaceNotFoundException("Race not found with id: " + id));
        log.info("Successfully retrieved race with id: {}", id);
        return ResponseEntity.ok(race);
    }
}
