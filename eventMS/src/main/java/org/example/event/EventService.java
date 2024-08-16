package org.example.event;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.ProjectServiceClient;
import org.example.client.UserServiceClient;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
import org.example.event.dto.ProjectResponse;
import org.example.event.dto.UserResponse;
import org.example.event_type.EventType;
import org.example.event_type.EventTypeRepository;
import org.example.event_type.EventTypeService;
import org.example.event_type.dto.EventTypeResponse;
import org.example.mappers.EventMapper;
import org.example.mappers.EventTypeMapper;
import org.example.mappers.StatusMapper;
import org.example.status.Status;
import org.example.status.StatusRepository;
import org.example.status.StatusService;
import org.example.status.dto.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public void addParticipant(Integer userId, Integer eventId) {
        Event event = findEventById(eventId);
        event.getParticipantIds().add(userId);

        eventRepository.save(event);
    }

    public void removeParticipant(Integer userId, Integer eventId) {
        Event event = findEventById(eventId);
        event.getParticipantIds().removeIf(e -> e.equals(userId));

        eventRepository.save(event);
    }

    public Set<EventResponse> all() {
        Set<Event> events = new HashSet<>(eventRepository.findAll());
        return EventMapper.INSTANCE.toDtos(events);
    }

    public Set<EventResponse> allForUser(HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        assert user != null;
        Set<Event> events = new HashSet<>(eventRepository.findByCreatedBy(user.getId()));
        return EventMapper.INSTANCE.toDtos(events);
    }

    public Set<EventResponse> allForProject(Integer memberId) {
        Set<Event> eventSet = eventRepository.findByProjectId(memberId);
        return EventMapper.INSTANCE.toDtos(eventSet);
    }

    public Event findEventById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No found"));
    }

    public EventResponse getById(Integer id) {
        return EventMapper.INSTANCE.toDto(findEventById(id));
    }

    public EventResponse createPrivate(EventRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));
        Status status = statusRepository.findById(request.getStatusId()).orElseThrow(
                () -> new EntityNotFoundException("Status with id: " + request.getStatusId() + " not found"));
        Event event = new Event();
        ProjectResponse projectResponse = projectServiceClient.getProjectById(request.getProjectId()).getBody();

        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setPublic(false);
        event.setStatus(status);
        assert projectResponse != null;
        event.setProjectId(request.getProjectId());
        event.setDescription(request.getDescription());
        assert user != null;
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse participate(Integer eventId, HttpHeaders headers) {
        Event event = copyEventLocal(eventId, headers);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        assert user != null;
        addParticipant(user.getId(), event.getId());
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse unparticipate(Integer eventId, HttpHeaders headers) {
        Event publicEvent = findEventById(eventId);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        assert user != null;
        removeParticipant(user.getId(), publicEvent.getId());
        return EventMapper.INSTANCE.toDto(publicEvent);
    }

    public EventResponse copyEvent(Integer eventId, HttpHeaders headers) {
        return EventMapper.INSTANCE.toDto(copyEventLocal(eventId, headers));
    }

    private Event copyEventLocal(Integer eventId, HttpHeaders headers) {
        Event publicEvent = findEventById(eventId);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = publicEvent.getEventType();

        Event event = new Event();
        event.setName(publicEvent.getName());
        event.setLink(publicEvent.getLink());
        event.setEventType(eventType);
        event.setPublic(false);
        event.setStatus(publicEvent.getStatus());
        event.setProjectId(publicEvent.getProjectId());
        event.setDescription(publicEvent.getDescription());
        assert user != null;
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(publicEvent.getStartDate());
        event.setEndDate(publicEvent.getEndDate());

        return eventRepository.save(event);
    }

    public EventResponse createPublic(EventRequest request, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));

        ProjectResponse projectResponse = projectServiceClient.getProjectById(request.getProjectId()).getBody();

        Event event = new Event();
        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setPublic(true);
        event.setStatus(null);
        assert projectResponse != null;
        event.setProjectId(request.getProjectId());
        event.setDescription(request.getDescription());
        assert user != null;
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse update(Integer id, EventRequest request, HttpHeaders headers) {
        Event event = findEventById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));
        Status status = statusRepository.findById(request.getStatusId()).orElseThrow(
                () -> new EntityNotFoundException("Status with id: " + request.getStatusId() + " not found"));        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setStatus(status);
        event.setDescription(request.getDescription());
        assert user != null;
        event.setUpdatedBy(user.getId());
        event.setUpdatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public void delete(Integer id) {
        eventRepository.deleteById(id);
    }
}
