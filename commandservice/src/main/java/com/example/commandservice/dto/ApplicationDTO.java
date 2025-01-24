package com.example.commandservice.dto;

import lombok.Data;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ApplicationDTO {
    private UUID id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String club;

    @NotNull(message = "Race ID is required")
    private UUID raceId;

    private UUID userId;
}
