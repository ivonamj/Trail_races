package com.example.queryservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RaceDTO {
    private UUID id;
    private String name;
    private String distance;
}
