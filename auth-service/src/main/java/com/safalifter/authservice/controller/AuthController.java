package com.safalifter.authservice.controller;

import com.safalifter.authservice.dto.RegisterDto;
import com.safalifter.authservice.dto.TokenDto;
import com.safalifter.authservice.request.LoginRequest;
import com.safalifter.authservice.request.RegisterRequest;
import com.safalifter.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("${base-path}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        RegisterDto user = null;
        try {
            user = authService.register(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok(user);

    }

    @GetMapping
    public ResponseEntity<String> getUsername(@RequestHeader HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("Authorization")).get(0).substring(7);
        return ResponseEntity.ok(authService.getUsernameFromBearer(token));
    }
}
