package com.mateus.scontrinoapi.services;

import com.mateus.scontrinoapi.dto.ExpenseDTO;
import com.mateus.scontrinoapi.dto.ExpenseResponseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.entities.Expense.Expense;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.repositories.CategoryRepository;
import com.mateus.scontrinoapi.repositories.ExpenseRepository;
import com.mateus.scontrinoapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository repository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ExpenseResponseDTO> listAll() {
        List<Expense> expenses = this.repository.findAll();

        return expenses.stream().map(ExpenseResponseDTO::new).toList();
    }

    public List<ExpenseResponseDTO> listAllFromUser(String email) {
        List<Expense> expenses = this.repository.findByUserEmail(email);

        return expenses.stream().map(ExpenseResponseDTO::new).toList();
    }

    public ExpenseResponseDTO create(ExpenseDTO data, String email) {
        User user = userRepository.findByEmail(email);

        Category category = categoryRepository.findById(data.categoryId())
                .orElseThrow();

        return new ExpenseResponseDTO(this.repository.save(new Expense(
                data.description(),
                data.amount(),
                category,
                user,
                data.date()
        )));
    }

}
