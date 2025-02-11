package com.mvc.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String role;
    private String message;
}
