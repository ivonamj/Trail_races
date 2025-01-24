package com.example.queryservice.controller;

import com.example.queryservice.dto.ApplicationDTO;
import com.example.queryservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForUser(@RequestParam UUID userId) {
        log.info("Received request to get applications for userId: {}", userId);
        List<ApplicationDTO> applications = applicationService.getApplicationsForUser(userId);
        log.info("Successfully retrieved {} applications for userId: {}", applications.size(), userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable UUID id) {
        log.info("Received request to get application with id: {}", id);
        ApplicationDTO application = applicationService.getApplicationById(id);
        log.info("Successfully retrieved application with id: {}", id);
        return ResponseEntity.ok(application);
    }
}
