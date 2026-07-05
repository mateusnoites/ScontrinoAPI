package com.mateus.scontrinoapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseDTO(
        String description,
        BigDecimal amount,
        Long categoryId,
        LocalDateTime date
) {
}
