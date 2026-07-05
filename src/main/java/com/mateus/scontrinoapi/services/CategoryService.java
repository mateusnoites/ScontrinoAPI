package com.mateus.scontrinoapi.services;

import com.mateus.scontrinoapi.dto.CategoryResponseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.dto.CategoryDTO;
import com.mateus.scontrinoapi.entities.Expense.Expense;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.repositories.CategoryRepository;
import com.mateus.scontrinoapi.repositories.ExpenseRepository;
import com.mateus.scontrinoapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public CategoryService(CategoryRepository repository, UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<CategoryResponseDTO> listAll() {
        List<Category> categories = this.repository.findAll();

        return categories.stream().map(CategoryResponseDTO::new).toList();
    }

    public List<CategoryResponseDTO> listAllFromUser(String email) {
        List<Category> categories = this.repository.findByUserEmail(email);

        return categories.stream().map(CategoryResponseDTO::new).toList();
    }

    public CategoryResponseDTO create(CategoryDTO data, String email) {

        User user = (User) userRepository.findByEmail(email);

        if (this.repository.existsByNameAndUserId(data.name(), user.getId()))
            throw new BusinessException("Categoria já existe");

        Category newCategory = new Category(data.name(), user);

        newCategory = this.repository.save(newCategory);

        return new CategoryResponseDTO(newCategory);
    }

    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow();

        // deletando todos os gastos que têm essa categoria
        List<Expense> catExpensesList = expenseRepository.findAllByCategoryId(id);
        expenseRepository.deleteAll(catExpensesList);

        this.repository.delete(category);
    }

    public CategoryResponseDTO update(Long id, CategoryDTO data, String email) {
        User user = userRepository.findByEmail(email);
        Category updatedCategory = new Category(id, data.name(), user);
        return new CategoryResponseDTO(this.repository.save(updatedCategory));
    }

}
