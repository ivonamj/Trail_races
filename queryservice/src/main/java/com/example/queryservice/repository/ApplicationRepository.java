package com.example.queryservice.repository;

import com.example.queryservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByRaceId(UUID raceId);

    List<Application> findByUserId(UUID userId);
}
