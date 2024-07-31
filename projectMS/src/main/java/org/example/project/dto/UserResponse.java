package org.example.project.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String email;
    private String username;
    private String role;
}
