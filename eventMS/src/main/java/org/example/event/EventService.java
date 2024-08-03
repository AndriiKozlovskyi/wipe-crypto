package org.example.event;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.UserServiceClient;
import org.example.event.dto.EventRequest;
import org.example.event.dto.EventResponse;
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

    public EventResponse createPrivate(EventRequest request, HttpHeaders headers, Integer projectId) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));
        Set<Status> statuses = new HashSet<>(statusRepository.findAllById(new ArrayList<>(request.getStatusesIds())));

        Event event = new Event();
        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setPublic(false);
        event.setStatuses(statuses);
        event.setProjectId(projectId);
        event.setDescription(request.getDescription());
        assert user != null;
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse saveEvent(Integer eventId, HttpHeaders headers) {
        Event publicEvent = findEventById(eventId);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = publicEvent.getEventType();
        Set<Status> statuses = new HashSet<>(publicEvent.getStatuses());

        Event event = new Event();
        event.setName(publicEvent.getName());
        event.setLink(publicEvent.getLink());
        event.setEventType(eventType);
        event.setPublic(false);
        event.setStatuses(statuses);
        event.setProjectId(publicEvent.getProjectId());
        event.setDescription(publicEvent.getDescription());
        assert user != null;
        event.setCreatedBy(user.getId());
        event.setCreatedAt(OffsetDateTime.now());
        event.setStartDate(publicEvent.getStartDate());
        event.setEndDate(publicEvent.getEndDate());

        eventRepository.save(event);
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse createPublic(EventRequest request, HttpHeaders headers, Integer projectId) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        EventType eventType = eventTypeRepository.findById(request.getEventTypeId()).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + request.getEventTypeId() + " not found"));
        Set<Status> statuses = new HashSet<>(statusRepository.findAllById(new ArrayList<>(request.getStatusesIds())));

        Event event = new Event();
        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setPublic(true);
        event.setStatuses(statuses);
        event.setProjectId(projectId);
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
        Set<Status> statuses = new HashSet<>(statusRepository.findAllById(new ArrayList<>(request.getStatusesIds())));
        event.setName(request.getName());
        event.setLink(request.getLink());
        event.setEventType(eventType);
        event.setStatuses(statuses);
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
