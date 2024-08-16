package org.example.client;

import org.example.account.dtos.TaskRequest;
import org.example.account.dtos.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "task-service")
public interface TaskServiceClient {
    @GetMapping("/api/v1/task")
    ResponseEntity<TaskResponse> createTask(@RequestParam Integer eventId, @RequestParam Integer accountId, @RequestBody TaskRequest taskRequest, @RequestHeader HttpHeaders headers);

    @GetMapping("/api/v1/task")
    ResponseEntity<Set<TaskResponse>> allForEvent(@RequestParam Integer eventId);

}
