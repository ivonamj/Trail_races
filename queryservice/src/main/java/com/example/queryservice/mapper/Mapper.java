package com.example.queryservice.mapper;

import com.example.queryservice.dto.ApplicationDTO;
import com.example.queryservice.dto.RaceDTO;
import com.example.queryservice.model.Application;
import com.example.queryservice.model.Race;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public RaceDTO toRaceDTO(Race race) {
        RaceDTO dto = new RaceDTO();
        dto.setId(race.getId());
        dto.setName(race.getName());
        dto.setDistance(race.getDistance().name());
        return dto;
    }

    public ApplicationDTO toApplicationDTO(Application application) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(application.getId());
        dto.setFirstName(application.getFirstName());
        dto.setLastName(application.getLastName());
        dto.setClub(application.getClub());
        dto.setRaceId(application.getRaceId());
        dto.setUserId(application.getUserId());
        return dto;
    }
}
