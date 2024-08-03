package org.example.mappers;

import org.example.event_type.EventType;
import org.example.event_type.dto.EventTypeRequest;
import org.example.event_type.dto.EventTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, EventMapper.class })
public interface EventTypeMapper extends BaseDtoMapper<EventType, EventTypeRequest, EventTypeResponse> {
    EventTypeMapper INSTANCE = Mappers.getMapper(EventTypeMapper.class);
}
