package com.example.commandservice.repository;

import com.example.commandservice.model.Application;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    @Modifying
    @Transactional
    void deleteByRaceId(@Param("raceId") UUID raceId);
}
