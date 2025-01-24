package com.example.commandservice.controller;

import com.example.commandservice.dto.RaceDTO;
import com.example.commandservice.service.RaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/races")
@RequiredArgsConstructor
@Slf4j
public class RaceController {

    private final RaceService raceService;

    @PostMapping
    public ResponseEntity<RaceDTO> createRace(@RequestBody @Valid RaceDTO raceDTO) {
        log.info("Received request to create race: {}", raceDTO);
        RaceDTO createdRace = raceService.createRace(raceDTO);
        log.info("Race created successfully with ID: {}", createdRace.getId());
        return ResponseEntity.ok(createdRace);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RaceDTO> updateRace(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        log.info("Received request to update race with ID: {}. Updates: {}", id, updates);
        RaceDTO updatedRace = raceService.updateRace(id, updates);
        log.info("Race with ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedRace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable UUID id) {
        log.info("Received request to delete race with ID: {}", id);
        raceService.deleteRace(id);
        log.info("Race with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
