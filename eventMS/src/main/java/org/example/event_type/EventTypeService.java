package org.example.event_type;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.UserServiceClient;
import org.example.event.Event;
import org.example.event.EventService;
import org.example.event.dto.UserResponse;
import org.example.event_type.dto.EventTypeRequest;
import org.example.event_type.dto.EventTypeResponse;
import org.example.mappers.EventTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class EventTypeService {
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    EventService eventService;

    @Autowired
    UserServiceClient userServiceClient;

    public Set<EventTypeResponse> all() {
        Set<EventType> eventTypeSet = new HashSet<>(eventTypeRepository.findAll());
        return EventTypeMapper.INSTANCE.toDtos(eventTypeSet);
    }

    public EventType findEventTypeById(Integer id) {
        return eventTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("EventType with id: " + id + " not found"));
    }

    public EventTypeResponse getById(Integer id) {
        return EventTypeMapper.INSTANCE.toDto(findEventTypeById(id));
    }

    public EventTypeResponse create(EventTypeRequest eventTypeRequest, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        EventType eventType = new EventType();
        eventType.setName(eventTypeRequest.getName());
        assert user != null;
        eventType.setCreatedBy(user.getId());
        eventType.setCreatedAt(OffsetDateTime.now());
        eventTypeRepository.save(eventType);
        return EventTypeMapper.INSTANCE.toDto(eventType);
    }

    public EventTypeResponse update(Integer id, EventTypeRequest request, HttpHeaders headers) {
        EventType eventType = findEventTypeById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        eventType.setName(request.getName());
        assert user != null;
        eventType.setUpdatedBy(user.getId());
        eventType.setUpdatedAt(OffsetDateTime.now());
        eventTypeRepository.save(eventType);
        return EventTypeMapper.INSTANCE.toDto(eventType);
    }

    public void delete(Integer id) {
        EventType eventType = findEventTypeById(id);
        eventType.setEvents(null);
        eventTypeRepository.save(eventType);
        eventTypeRepository.deleteById(id);
    }

    public void deleteForEvent(Integer eventId) {
        Event event = eventService.findEventById(eventId);
        EventType eventType = event.getEventType();
        eventType.getEvents().remove(event);
        eventTypeRepository.save(eventType);
    }
}
