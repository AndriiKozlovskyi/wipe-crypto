package org.example.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PostMapping("/api/v1/user")
    BaseApiResponse<?> createUser(@RequestBody RegisterRequest request);

    @GetMapping("/api/v1/user/findBy/{identifier}")
    BaseApiResponse<UserDto> getUserByIdentifier(@PathVariable String identifier);
}
