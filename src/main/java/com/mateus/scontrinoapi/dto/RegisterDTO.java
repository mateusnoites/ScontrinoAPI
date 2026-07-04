package com.mateus.scontrinoapi.dto;

import com.mateus.scontrinoapi.entities.User.UserRole;

public record RegisterDTO(
        String name,
        String email,
        String password,
        UserRole role
) {
}
