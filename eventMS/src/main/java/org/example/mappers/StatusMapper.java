package org.example.mappers;

import org.example.project.Project;
import org.example.project.dto.ProjectRequest;
import org.example.project.dto.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class })
public interface StatusMapper extends BaseDtoMapper<Project, ProjectRequest, ProjectResponse> {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);
}
