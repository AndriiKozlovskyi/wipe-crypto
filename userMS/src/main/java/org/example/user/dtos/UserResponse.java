package org.example.user.dtos;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String email;
    private String username;
    private String role;
}
