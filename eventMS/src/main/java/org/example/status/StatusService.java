package org.example.status;

import jakarta.persistence.EntityNotFoundException;
import org.example.client.UserServiceClient;
import org.example.event.Event;
import org.example.event.EventService;
import org.example.event.dto.UserResponse;
import org.example.status.Status;
import org.example.status.dto.StatusRequest;
import org.example.status.dto.StatusResponse;
import org.example.mappers.StatusMapper;
import org.example.status.dto.StatusRequest;
import org.example.status.dto.StatusResponse;
import org.example.mappers.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    UserServiceClient userServiceClient;
    @Autowired
    EventService eventService;

    public Set<StatusResponse> all() {
        Set<Status> statusSet = new HashSet<>(statusRepository.findAll());
        return StatusMapper.INSTANCE.toDtos(statusSet);
    }

    public Set<Status> findStatusesByIds(Set<Integer> ids) {
        return new HashSet<>(statusRepository.findAllById(new ArrayList<>(ids)));
    }

    public Status findStatusById(Integer id) {
        return statusRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Status with id: " + id + " not found"));
    }

    public StatusResponse getById(Integer id) {
        return StatusMapper.INSTANCE.toDto(findStatusById(id));
    }

    public StatusResponse create(StatusRequest statusRequest, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Status status = new Status();
        status.setName(statusRequest.getName());
        assert user != null;
        status.setCreatedBy(user.getId());
        status.setCreatedAt(OffsetDateTime.now());
        statusRepository.save(status);
        return StatusMapper.INSTANCE.toDto(status);
    }

    public StatusResponse update(Integer id, StatusRequest request, HttpHeaders headers) {
        Status status = findStatusById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        status.setName(request.getName());
        assert user != null;
        status.setUpdatedBy(user.getId());
        status.setUpdatedAt(OffsetDateTime.now());
        statusRepository.save(status);
        return StatusMapper.INSTANCE.toDto(status);
    }

    public void delete(Integer id) {
        statusRepository.deleteById(id);
    }

}
