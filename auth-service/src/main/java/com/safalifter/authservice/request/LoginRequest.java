package com.safalifter.authservice.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String identifier;
    private String password;
}
