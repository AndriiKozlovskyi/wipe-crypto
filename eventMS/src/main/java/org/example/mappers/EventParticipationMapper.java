package org.example.mappers;

import org.example.event.EventParticipation;
import org.example.event.dto.EventParticipationRequest;
import org.example.event.dto.EventParticipationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, EventMapper.class })
public interface EventParticipationMapper extends BaseDtoMapper<EventParticipation, EventParticipationRequest, EventParticipationResponse> {
    EventParticipationMapper INSTANCE = Mappers.getMapper(EventParticipationMapper.class);
}
