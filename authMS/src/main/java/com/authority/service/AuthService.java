package com.authority.service;


import com.authority.dtos.requests.AuthRequest;
import com.authority.dtos.requests.RegisterRequest;
import com.authority.dtos.responses.AuthResponse;
import com.authority.entity.Role;
import com.authority.entity.User;
import com.authority.enums.RoleType;
import com.authority.repository.RoleRepository;
import com.authority.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    public AuthResponse register(RegisterRequest request) {

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with username " + request.getUsername() + " already exists");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }

        if (request.getRole() == null) {
            request.setRole("USER");
        }

        Role role = roleService.getRoleByRoleType(request.getRole());
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getIdentifier(),
                    request.getPassword()
            )
        );

        var user = repository.findByUsername(request.getIdentifier())
                .or(() -> repository.findByEmail(request.getIdentifier()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
