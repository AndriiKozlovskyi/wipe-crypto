package com.safalifter.authservice.client;

import com.safalifter.authservice.dto.BaseApiResponse;
import com.safalifter.authservice.dto.RegisterDto;
import com.safalifter.authservice.dto.UserDto;
import com.safalifter.authservice.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PostMapping("/api/v1/user")
    ResponseEntity<RegisterDto> createUser(@RequestBody RegisterRequest request);

    @GetMapping("/api/v1/user/findBy/{identifier}")
    ResponseEntity<UserDto> getUserByIdentifier(@PathVariable String identifier);
}