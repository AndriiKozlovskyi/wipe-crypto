package org.example.mappers;

import org.example.task.Task;
import org.example.task.dtos.TaskRequest;
import org.example.task.dtos.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class })
public interface TaskMapper extends BaseDtoMapper<Task, TaskRequest, TaskResponse> {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
}
