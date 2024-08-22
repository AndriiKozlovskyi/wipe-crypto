package org.example.mappers;

import org.example.event.Event;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, StatusMapper.class, EventTypeMapper.class, EventParticipationMapper.class })
public interface EventMapper extends BaseDtoMapper<Event, EventRequest, EventResponse> {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
