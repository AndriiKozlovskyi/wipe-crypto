package org.example.event.dto;

import lombok.Data;

@Data
public class EventParticipationResponse {
    private Integer id;
    private Integer event;
    private Integer participantId;
}
