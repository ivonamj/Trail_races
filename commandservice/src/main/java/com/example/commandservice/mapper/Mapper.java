package com.example.commandservice.mapper;

import com.example.commandservice.dto.ApplicationDTO;
import com.example.commandservice.dto.RaceDTO;
import com.example.commandservice.model.Application;
import com.example.commandservice.model.Distance;
import com.example.commandservice.model.Race;
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

    public Race toRaceEntity(RaceDTO dto) {
        Race race = new Race();
        race.setId(dto.getId());
        race.setName(dto.getName());
        race.setDistance(Distance.valueOf(dto.getDistance()));
        return race;
    }

    public Application toApplicationEntity(ApplicationDTO dto) {
        Application application = new Application();
        application.setId(dto.getId());
        application.setFirstName(dto.getFirstName());
        application.setLastName(dto.getLastName());
        application.setClub(dto.getClub());
        application.setRaceId(dto.getRaceId());
        application.setUserId(dto.getUserId());
        return application;
    }
}
