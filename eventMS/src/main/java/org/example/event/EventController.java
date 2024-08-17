package org.example.event;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
import org.example.exceptions.NoPermissionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Event API", description = "")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/forUser")
    public ResponseEntity<Set<EventResponse>> allForUser(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(eventService.allForUser(headers));
    }

    @GetMapping
    public ResponseEntity<?> getEvents(
            @RequestParam(required = false) Integer projectId,
            @RequestParam(required = false) Integer id
    ) {
        Set<EventResponse> events = null;

        try {
            if (projectId != null) {
                events = eventService.allForProject(projectId);
            } else if (id != null) {
                return ResponseEntity.ok(eventService.getById(id));
            } else {
                events = eventService.all();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(events);
    }

    @PostMapping("/participate/{eventId}")
    public ResponseEntity<EventResponse> participate(@PathVariable Integer eventId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(eventService.participate(eventId, headers));
    }

    @PostMapping("/unparticipate/{eventId}")
    public ResponseEntity<EventResponse> unparticipate(@PathVariable Integer eventId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(eventService.unparticipate(eventId, headers));
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventRequest eventRequest, @RequestHeader HttpHeaders headers) {
        try {
            return ResponseEntity.ok(eventService.create(eventRequest, headers));
        } catch (NoPermissionsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/changeVisibility/{id}")
    public ResponseEntity<EventResponse> changeVisibility(@PathVariable Integer id, @RequestParam String visibility, @RequestHeader HttpHeaders headers) {
        EventResponse event = null;
        try {
            if (visibility.equals("public")) {
                event = eventService.makePublic(id, headers);
            } else {
                event = eventService.makePrivate(id, headers);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@RequestBody EventRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        EventResponse event = null;
        try {
            event = eventService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        try {
            eventService.delete(id, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoPermissionsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }
}