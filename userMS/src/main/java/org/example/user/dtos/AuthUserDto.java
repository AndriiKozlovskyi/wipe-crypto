package org.example.user.dtos;

import lombok.Data;
import org.example.enums.Role;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
