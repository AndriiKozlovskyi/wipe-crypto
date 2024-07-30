package com.authority.controller;

import com.authority.dtos.requests.AuthRequest;
import com.authority.dtos.requests.RegisterRequest;
import com.authority.dtos.responses.AuthResponse;
import com.authority.service.AuthService;
import com.authority.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${base-path}/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    AuthService service;
    @Autowired
    JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("Hello world!", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        return new ResponseEntity<>(service.usernameExists(username), HttpStatus.OK);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        return new ResponseEntity<>(service.emailExists(email), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/isTokenExpired")
    public ResponseEntity<?> validateToken(@RequestBody AuthResponse token) {
        try {
            if(jwtService.isTokenExpired(token.getToken())) {
                return new ResponseEntity<>("{\"expired\"" + ":" + true + "}", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("{\"expired\"" + ":" + false + "}", HttpStatus.OK);
    }
}
