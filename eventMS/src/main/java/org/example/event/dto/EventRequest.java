package org.example.event.dto;

import lombok.Data;

@Data
public class EventRequest {
    private String name;
    private String link;
    private String description;
}
