package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.CategoryDTO;
import com.mateus.scontrinoapi.dto.CategoryResponseDTO;
import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.infra.components.AuthUtil;
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
    private final AuthUtil authUtil;

    public CategoryController(CategoryService service, AuthUtil authUtil) {
        this.service = service;
        this.authUtil = authUtil;
    }

    @GetMapping("/general")
    public ResponseEntity<List<CategoryResponseDTO>> listAll() {
        List<CategoryResponseDTO> categoryList = this.service.listAll();

        return ResponseEntity.ok(categoryList);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listAllFromUser() {
        String email = this.authUtil.getEmail();

        List<CategoryResponseDTO> categoryList = this.service.listAllFromUser(email);

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<CategoryResponseDTO>(this.service.create(data, email), HttpStatus.CREATED);
    }

}
