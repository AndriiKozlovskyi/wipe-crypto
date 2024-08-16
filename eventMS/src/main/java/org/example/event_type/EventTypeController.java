package org.example.event_type;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.event_type.dto.EventTypeRequest;
import org.example.event_type.dto.EventTypeResponse;
import org.example.status.dto.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/eventType")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "EventType API", description = "")
public class EventTypeController {
    @Autowired
    EventTypeService eventTypeService;

    @GetMapping
    public ResponseEntity<?> getEventTypes(
            @RequestParam(required = false) Integer id
    ) {
        Set<EventTypeResponse> eventTypeResponses = null;

        try {
            if (id != null) {
                return ResponseEntity.ok(eventTypeService.getById(id));
            } else {
                eventTypeResponses = eventTypeService.all();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(eventTypeResponses);
    }

    @PostMapping
    public ResponseEntity<EventTypeResponse> createEventType(@RequestBody EventTypeRequest eventTypeRequest, @RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(eventTypeService.create(eventTypeRequest, headers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTypeResponse> updateEventType(@RequestBody EventTypeRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        EventTypeResponse event = null;
        try {
            event = eventTypeService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventType(@PathVariable Integer id) {
        try {
            eventTypeService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}