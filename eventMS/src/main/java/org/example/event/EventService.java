package org.example.event;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.ProjectServiceClient;
import org.example.client.UserServiceClient;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
import org.example.event.dto.ProjectResponse;
import org.example.event.dto.UserResponse;
import org.example.exceptions.NoPermissionsException;
import org.example.event_type.EventType;
import org.example.event_type.EventTypeRepository;
import org.example.mappers.EventMapper;
import org.example.status.Status;
import org.example.status.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserServiceClient userServiceClient;
    @Autowired
    ProjectServiceClient projectServiceClient;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    EventParticipationRepository eventParticipationRepository;

    private final String DEFAULT_STATUS = "todo";

    public Set<EventResponse> all() {
        Set<Event> events = new HashSet<>(eventRepository.findAll());
        return EventMapper.INSTANCE.toDtos(events);
    }

    public Set<EventResponse> getUserEvents(HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        if (user == null) {
            throw new IllegalStateException("User information could not be retrieved");
        }

        Integer userId = user.getId();

        Set<Event> createdEvents = eventRepository.findByCreatedBy(userId);

        Set<Event> participatedEvents = eventParticipationRepository.findEventsByParticipantId(userId);

        Set<Event> allEvents = new HashSet<>(createdEvents);
        allEvents.addAll(participatedEvents);

        return EventMapper.INSTANCE.toDtos(allEvents);
    }
    public Set<EventResponse> allForProject(Integer projectId) {
        Set<Event> eventSet = eventRepository.findByProjectId(projectId);
        return EventMapper.INSTANCE.toDtos(eventSet);
    }

    public Event findEventById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
    }

    public EventResponse getById(Integer id) {
        return EventMapper.INSTANCE.toDto(findEventById(id));
    }

    public EventResponse create(EventRequest request, HttpHeaders headers) throws NoPermissionsException {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        assert user != null;

        if (!user.getRole().equals("ADMIN") && !user.getRole().equals("INFL") && request.isPublic()) {
            throw new NoPermissionsException("You have no rights (^_−)");
        }

        EventType eventType = eventTypeRepository.findById(request.getEventTypeId())
                .orElseThrow(() -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));

        Event event = new Event();
        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setDescription(request.getDescription());
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setPublic(request.isPublic());

        try {
            ResponseEntity<ProjectResponse> response = projectServiceClient.getProjectById(request.getProjectId());
            event.setProjectId(request.getProjectId());
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Project not found, proceeding without project association.");
        } catch (Exception e) {
            System.out.println("Error occurred while fetching project: " + e.getMessage());
        }

        if (request.isPublic()) {
            Status status = statusRepository.findByName(DEFAULT_STATUS)
                    .orElseThrow(() -> new EntityNotFoundException("Status with name: " + DEFAULT_STATUS + " not found"));
            event.setStatus(status);
        } else {
            event.setStatus(null);
        }

        eventRepository.save(event);
        return participateInEvent(event.getId(), headers);
    }

    public EventResponse changeVisibility(Integer eventId, boolean isPublic, HttpHeaders headers) throws AccessDeniedException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        if (user == null) {
            throw new IllegalStateException("User information could not be retrieved");
        }

        if (!event.getCreatedBy().equals(user.getId()) && !user.getRole().equals("ADMIN") && !user.getRole().equals("INFL")) {
            throw new AccessDeniedException("You are not authorized to make this event public");
        }

        event.setPublic(isPublic);
        event.setUpdatedBy(user.getId());
        event.setUpdatedAt(OffsetDateTime.now());

        eventRepository.save(event);

        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse participateInEvent(Integer eventId, HttpHeaders headers) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        UserResponse participant = userServiceClient.getUserFromHeaders(headers).getBody();

        if (participant == null) {
            throw new IllegalStateException("Participant information could not be retrieved");
        }

        Optional<EventParticipation> existingParticipation = eventParticipationRepository.findByEventIdAndParticipantId(eventId, participant.getId());

        if (existingParticipation.isPresent()) {
            return EventMapper.INSTANCE.toDto(event);
        }

        EventParticipation participation = EventParticipation.builder()
                .event(event)
                .participantId(participant.getId())
                .build();

        event.getParticipations().add(participation);

        eventRepository.save(event);

        return EventMapper.INSTANCE.toDto(event);
    }


    public EventResponse unparticipateInEvent(Integer eventId, HttpHeaders headers) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        UserResponse participant = userServiceClient.getUserFromHeaders(headers).getBody();

        if (participant == null) {
            throw new IllegalStateException("Participant information could not be retrieved");
        }

        Optional<EventParticipation> existingParticipation = eventParticipationRepository.findByEventIdAndParticipantId(eventId, participant.getId());

        if (existingParticipation.isPresent()) {
            EventParticipation participation = existingParticipation.get();
            event.getParticipations().remove(participation);
            eventParticipationRepository.delete(participation); // Remove the participation from the database

            eventRepository.save(event);

            return EventMapper.INSTANCE.toDto(event);
        } else {
            throw new EntityNotFoundException("Participant not found in the event");
        }
    }


    public EventResponse update(Integer id, EventRequest request, HttpHeaders headers) throws NoPermissionsException {
        Event event = findEventById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        assert user != null;
        if (!Objects.equals(event.getCreatedBy(), user.getId())) {
            throw new NoPermissionsException("You have no rights (^_−)");
        }

        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));
        Status status = statusRepository.findById(request.getStatusId()).orElseThrow(
                () -> new EntityNotFoundException("Status with id: " + request.getStatusId() + " not found"));        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setStatus(status);
        event.setDescription(request.getDescription());
        event.setUpdatedBy(user.getId());
        event.setUpdatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public void delete(Integer id, HttpHeaders headers) throws NoPermissionsException {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        Event event = findEventById(id);

        assert user != null;
        if (!Objects.equals(event.getCreatedBy(), user.getId())) {
            throw new NoPermissionsException("You have no rights (^_−)");
        }
        eventRepository.deleteById(id);
    }
}
