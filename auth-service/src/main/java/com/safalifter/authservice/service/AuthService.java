package com.safalifter.authservice.service;

import com.safalifter.authservice.client.UserServiceClient;
import com.safalifter.authservice.dto.BaseApiResponse;
import com.safalifter.authservice.dto.RegisterDto;
import com.safalifter.authservice.dto.TokenDto;
import com.safalifter.authservice.exc.WrongCredentialsException;
import com.safalifter.authservice.request.LoginRequest;
import com.safalifter.authservice.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    UserServiceClient userServiceClient;
    @Autowired
    JwtService jwtService;

    public TokenDto login(LoginRequest request) {
        var user = userServiceClient.getUserByIdentifier(request.getIdentifier());
        var jwtToken = jwtService.generateToken(Objects.requireNonNull(user.getData()).getUsername());

        return TokenDto.builder()
                .token(jwtToken)
                .build();
    }

    public RegisterDto register(RegisterRequest request) throws Exception {
        BaseApiResponse<RegisterDto> response = userServiceClient.save(request);
        if (response.getStatusCode() == HttpStatus.OK.value()) {
            return response.getData();
        } else {
            String errorMessage = response.getErrorMessage();
            throw new Exception(errorMessage);
        }
    }
}
