package org.example.event;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Integer id) {
        EventResponse event = null;
        try {
            event = eventService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<EventResponse>> all() {
        return ResponseEntity.ok(eventService.all());
    }

    @GetMapping
    public ResponseEntity<Set<EventResponse>> allForProject(@RequestParam Integer projectId) {
        return ResponseEntity.ok(eventService.allForProject(projectId));
    }

    @PostMapping("/save/{eventId}")
    public ResponseEntity<EventResponse> saveEvent(@PathVariable Integer eventId, @RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(eventService.saveEvent(eventId, headers));
    }


    @PostMapping("/private")
    public ResponseEntity<EventResponse> createPrivateEvent(@RequestParam Integer projectId, @RequestBody EventRequest eventRequest, @RequestHeader HttpHeaders headers) {
    //for commit
        return ResponseEntity.ok(eventService.createPrivate(eventRequest, headers, projectId));
    }

    @PostMapping("/public")
    public ResponseEntity<EventResponse> createPublicEvent(@RequestParam Integer projectId, @RequestBody EventRequest eventRequest, @RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(eventService.createPublic(eventRequest, headers, projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@RequestBody EventRequest eventRequest, @PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        EventResponse event = null;
        try {
            event = eventService.update(id, eventRequest, headers);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{eventId}/addParticipant/{userId}")
    public ResponseEntity<String> addParticipant(@PathVariable Integer eventId, @PathVariable Integer userId) {
        try {
            eventService.addParticipant(userId, eventId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Participant now participates event");
    }

    @PutMapping("/{eventId}/removeParticipant/{userId}")
    public ResponseEntity<String> removeParticipant(@PathVariable Integer eventId, @PathVariable Integer userId) {
        try {
            eventService.removeParticipant(userId, eventId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Participant removed from event");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        try {
            eventService.delete(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}