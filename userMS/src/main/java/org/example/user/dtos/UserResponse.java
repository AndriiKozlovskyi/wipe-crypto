package org.example.user.dtos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserResponse {
    private Integer id;
    private String email;
    private String username;
//    private ArrayList<Integer> subscriptionsIds = new ArrayList<>();
//    private ArrayList<Integer> followersIds = new ArrayList<>();
    private String role;
}
