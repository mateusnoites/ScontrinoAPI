package com.mateus.scontrinoapi.dto;

import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.entities.User.User;

public record CategoryResponseDTO(
        Long id,
        String name,
        Long userId,
        String userEmail,
        String userName
) {
    public CategoryResponseDTO (Category category) {
        this(
            category.getId(),
            category.getName(),
            category.getUser().getId(),
            category.getUser().getEmail(),
            category.getUser().getName()
        );
    }
}
