package com.mateus.scontrinoapi.entities.User;

public enum UserRole {
    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
