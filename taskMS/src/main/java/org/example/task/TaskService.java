package org.example.task;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.UserResponse;
import org.example.client.UserServiceClient;
import org.example.mappers.TaskMapper;
import org.example.task.dtos.TaskRequest;
import org.example.task.dtos.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserServiceClient userServiceClient;

    public Set<TaskResponse> all() {
        Set<Task> taskSet = new HashSet<>(taskRepository.findAll());
        return TaskMapper.INSTANCE.toDtos(taskSet);
    }

    public Task findTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task with id: " + id + " not found"));
    }

    public TaskResponse getById(Integer id) {
        return TaskMapper.INSTANCE.toDto(findTaskById(id));
    }

    public Set<TaskResponse> getForEvent(Integer eventId) {
        Set<Task> taskSet = taskRepository.findByEventIdAndAccountId(eventId, null);
        return TaskMapper.INSTANCE.toDtos(taskSet);
    }

    public Set<TaskResponse> getForAccount(Integer accountId) {
        Set<Task> taskSet = taskRepository.findByAccountId(accountId);
        return TaskMapper.INSTANCE.toDtos(taskSet);
    }

    public TaskResponse create(Integer eventId, Integer accountId, TaskRequest taskRequest, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Task task = new Task();
        task.setAccountId(accountId);
        task.setEventId(eventId);
        task.setName(taskRequest.getName());
        assert user != null;
        task.setCreatedBy(user.getId());
        task.setCreatedAt(OffsetDateTime.now());
        taskRepository.save(task);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskResponse update(Integer id, Integer eventId, Integer accountId, TaskRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Task task = findTaskById(id);
        task.setAccountId(accountId);
        task.setEventId(eventId);
        task.setName(request.getName());
        task.setCompleted(task.isCompleted());
        assert user != null;
        task.setUpdatedBy(user.getId());
        task.setUpdatedAt(OffsetDateTime.now());
        taskRepository.save(task);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }

}
