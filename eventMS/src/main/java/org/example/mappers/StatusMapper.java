package org.example.mappers;

import org.example.status.Status;
import org.example.status.dto.StatusRequest;
import org.example.status.dto.StatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, EventMapper.class })
public interface StatusMapper extends BaseDtoMapper<Status, StatusRequest, StatusResponse> {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);
}
