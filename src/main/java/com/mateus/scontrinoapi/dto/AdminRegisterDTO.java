package com.mateus.scontrinoapi.dto;

import com.mateus.scontrinoapi.entities.User.UserRole;

public record AdminRegisterDTO(
        String name,
        String email,
        String password,
        UserRole role
) {
}
