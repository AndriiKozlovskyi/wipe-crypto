package org.example.status;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.status.dto.StatusRequest;
import org.example.status.dto.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/status")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Status API", description = "")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping("/{id}")
    public ResponseEntity<StatusResponse> getById(@PathVariable Integer id) {
        StatusResponse event = null;
        try {
            event = statusService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<StatusResponse>> all() {
        return ResponseEntity.ok(statusService.all());
    }

    @PostMapping
    public ResponseEntity<StatusResponse> createStatus(@RequestBody StatusRequest statusRequest, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(statusService.create(statusRequest, headers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusResponse> updateStatus(@RequestBody StatusRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        StatusResponse event = null;
        try {
            event = statusService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
        try {
            statusService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
