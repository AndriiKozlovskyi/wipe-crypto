package org.example.task;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.task.dtos.TaskRequest;
import org.example.task.dtos.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Task API", description = "")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Integer id) {
        TaskResponse event = null;
        try {
            event = taskService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<Set<TaskResponse>> allForEvent(@RequestParam Integer eventId) {
        Set<TaskResponse> event = null;
        try {
            event = taskService.getForEvent(eventId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<Set<TaskResponse>> allForAccount(@RequestParam Integer accountId) {
        Set<TaskResponse> event = null;
        try {
            event = taskService.getForAccount(accountId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }


    @GetMapping("/all")
    public ResponseEntity<Set<TaskResponse>> all() {
        return ResponseEntity.ok(taskService.all());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestParam Integer eventId, @RequestParam Integer accountId, @RequestBody TaskRequest accountRequest, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(taskService.create(eventId, accountId, accountRequest, headers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@RequestParam Integer eventId, @RequestParam Integer accountId, @RequestBody TaskRequest taskRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        TaskResponse event = null;
        try {
            event = taskService.update(id, eventId, accountId, taskRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        try {
            taskService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
