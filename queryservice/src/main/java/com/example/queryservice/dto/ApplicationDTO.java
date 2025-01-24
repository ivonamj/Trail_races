package com.example.queryservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ApplicationDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String club;
    private UUID raceId;
    private UUID userId;
}
