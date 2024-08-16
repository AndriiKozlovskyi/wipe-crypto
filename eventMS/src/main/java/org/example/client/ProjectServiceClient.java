package org.example.client;

import org.example.event.dto.ProjectResponse;
import org.example.event.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "project-service")
public interface ProjectServiceClient {
    @GetMapping("/api/v1/project/{id}")
    ResponseEntity<ProjectResponse> getProjectById(@PathVariable Integer id);
}
