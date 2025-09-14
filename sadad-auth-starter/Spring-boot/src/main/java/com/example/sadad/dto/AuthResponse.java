package com.example.sadad.dto;

import java.util.List;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private List<String> roles;

    public AuthResponse() {}

    public AuthResponse(String token, String refreshToken, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    // Getter & Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
