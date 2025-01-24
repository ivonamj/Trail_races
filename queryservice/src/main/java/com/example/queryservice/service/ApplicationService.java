package com.example.queryservice.service;

import com.example.queryservice.dto.ApplicationDTO;
import com.example.queryservice.exception.ApplicationNotFoundException;
import com.example.queryservice.mapper.Mapper;
import com.example.queryservice.model.Application;
import com.example.queryservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final Mapper mapper;

    public List<ApplicationDTO> getApplicationsForUser(UUID userId) {
        log.info("Fetching applications for userId: {}", userId);
        List<Application> applications = applicationRepository.findByUserId(userId);
        log.debug("Found {} applications for userId: {}", applications.size(), userId);
        List<ApplicationDTO> applicationDTOs = applications.stream()
                .map(mapper::toApplicationDTO)
                .toList();
        log.info("Successfully fetched {} applications for userId: {}", applicationDTOs.size(), userId);
        return applicationDTOs;
    }

    public ApplicationDTO getApplicationById(UUID id) {
        log.info("Fetching application with id: {}", id);
        ApplicationDTO application = applicationRepository.findById(id)
                .map(mapper::toApplicationDTO)
                .orElseThrow(() -> {
                    log.warn("Application not found with id: {}", id);
                    return new ApplicationNotFoundException("Application not found with id: " + id);
                });
        log.info("Successfully fetched application with id: {}", id);
        return application;
    }
}
