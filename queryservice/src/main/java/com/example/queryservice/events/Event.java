package com.example.queryservice.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String operation;
    private Object payload;
}
