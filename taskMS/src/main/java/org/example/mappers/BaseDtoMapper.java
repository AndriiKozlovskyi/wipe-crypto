package org.example.mappers;

import java.util.Set;

public interface BaseDtoMapper<Entity, RequestDto, ResponseDto> {

    Entity toEntity(RequestDto requestDto);
    ResponseDto toDto(Entity entity);

    Set<Entity> toEntities(Set<RequestDto> requestDtos);
    Set<ResponseDto> toDtos(Set<Entity> entities);
}
