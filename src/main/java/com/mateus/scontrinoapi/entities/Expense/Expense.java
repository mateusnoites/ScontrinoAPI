package com.mateus.scontrinoapi.entities.Expense;

import com.mateus.scontrinoapi.dto.ExpenseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.entities.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense(String description, BigDecimal amount, Category category, User user, LocalDateTime date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.user = user;

        if (date == null) this.date = LocalDateTime.now();
        else this.date = date;
    }
}
