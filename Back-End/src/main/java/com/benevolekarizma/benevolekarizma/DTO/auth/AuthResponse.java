package com.benevolekarizma.benevolekarizma.DTO.auth;

import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private UserResponse user;
    private String tokenType = "Bearer ";

    public AuthResponse(String accessToken, UserResponse user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
