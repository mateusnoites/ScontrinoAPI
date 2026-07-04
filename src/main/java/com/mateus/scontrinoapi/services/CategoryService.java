package com.mateus.scontrinoapi.services;

import com.mateus.scontrinoapi.dto.CategoryResponseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.dto.CategoryDTO;
import com.mateus.scontrinoapi.entities.User.User;
import com.mateus.scontrinoapi.exceptions.BusinessException;
import com.mateus.scontrinoapi.repositories.CategoryRepository;
import com.mateus.scontrinoapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

}
