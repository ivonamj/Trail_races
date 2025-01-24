package com.example.commandservice.service;

import com.example.commandservice.dto.ApplicationDTO;
import com.example.commandservice.events.EventPublisherService;
import com.example.commandservice.exception.ApplicationNotFoundException;
import com.example.commandservice.exception.UserNotFoundException;
import com.example.commandservice.mapper.Mapper;
import com.example.commandservice.model.Application;
import com.example.commandservice.model.User;
import com.example.commandservice.repository.ApplicationRepository;
import com.example.commandservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final EventPublisherService eventPublisherService;
    private final Mapper mapper;

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        log.debug("Creating application with data: {}", applicationDTO);
        Application application = mapper.toApplicationEntity(applicationDTO);
        Application savedApplication = applicationRepository.save(application);
        log.info("Application created with ID: {}", savedApplication.getId());

        eventPublisherService.publishEvent("application.create", savedApplication);
        log.debug("Published application.create event for application ID: {}", savedApplication.getId());

        return mapper.toApplicationDTO(savedApplication);
    }

    public void deleteApplication(UUID id) {
        log.debug("Deleting application with ID: {}", id);
        if (!applicationRepository.existsById(id)) {
            log.error("Application not found with ID: {}", id);
            throw new ApplicationNotFoundException("Application not found with ID: " + id);
        }

        applicationRepository.deleteById(id);
        log.info("Application with ID: {} deleted successfully.", id);

        eventPublisherService.publishEvent("application.delete", id);
        log.debug("Published application.delete event for application ID: {}", id);
    }

    public UUID getUserIdByEmail(String email) {
        log.debug("Retrieving user ID for email: {}", email);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found with email: {}", email);
            throw new UserNotFoundException("User not found with email: " + email);
        }

        log.debug("Found user ID: {} for email: {}", user.getId(), email);
        return user.getId();
    }
}
