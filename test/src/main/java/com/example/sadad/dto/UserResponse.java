package com.example.sadad.dto;

import java.util.List;

public class UserResponse {
    private Long id;
    private String username;
    private Boolean enabled;
    private List<String> roles;

    public UserResponse(Long id, String username, Boolean enabled, List<String> roles) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.roles = roles;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Boolean getEnabled() { return enabled; }
    public List<String> getRoles() { return roles; }
}
