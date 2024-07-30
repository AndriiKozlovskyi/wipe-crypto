package org.example.mappers;

import org.example.project.Project;
import org.example.project.dto.ProjectRequest;
import org.example.project.dto.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class })
public interface ProjectMapper extends BaseDtoMapper<Project, ProjectRequest, ProjectResponse> {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
}
