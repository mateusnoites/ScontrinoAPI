package com.mateus.scontrinoapi.dto;

import com.mateus.scontrinoapi.entities.Expense.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        LocalDateTime date,
        String categoryName,
        Long userId,
        String userEmail,
        String userName
) {
    public ExpenseResponseDTO (Expense expense) {
        this(
            expense.getId(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getDate(),
            expense.getCategory().getName(),
            expense.getUser().getId(),
            expense.getUser().getEmail(),
            expense.getUser().getName()
        );
    }
}
