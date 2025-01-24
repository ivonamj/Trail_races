package com.example.commandservice.controller;

import com.example.commandservice.dto.ApplicationDTO;
import com.example.commandservice.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody @Valid ApplicationDTO applicationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UUID userId = applicationService.getUserIdByEmail(email);

        log.info("User with email {} (ID: {}) is creating an application: {}", email, userId, applicationDTO);

        applicationDTO.setUserId(userId);
        ApplicationDTO createdApplication = applicationService.createApplication(applicationDTO);

        log.info("Application created successfully with ID: {}", createdApplication.getId());
        return ResponseEntity.ok(createdApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID id) {
        log.info("Received request to delete application with ID: {}", id);
        applicationService.deleteApplication(id);
        log.info("Application with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
