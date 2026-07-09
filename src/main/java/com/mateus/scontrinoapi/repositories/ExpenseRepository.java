package com.mateus.scontrinoapi.repositories;

import com.mateus.scontrinoapi.entities.Expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserEmail(String userEmail);
    List<Expense> findAllByCategoryId(Long categoryId);
    List<Expense> findByCategoryNameAndUserEmail(String categoryName, String userEmail);
}
