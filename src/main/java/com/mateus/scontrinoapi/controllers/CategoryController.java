package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.CategoryDTO;
import com.mateus.scontrinoapi.dto.CategoryResponseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/general")
    public ResponseEntity<List<CategoryResponseDTO>> listAll() {
        List<CategoryResponseDTO> categoryList = this.service.listAll();

        return ResponseEntity.ok(categoryList);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listAllFromUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        List<CategoryResponseDTO> categoryList = this.service.listAllFromUser(email);

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryDTO data) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return new ResponseEntity<CategoryResponseDTO>(this.service.create(data, email), HttpStatus.CREATED);
    }

}
