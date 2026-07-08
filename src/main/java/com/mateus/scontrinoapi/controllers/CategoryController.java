package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.CategoryDTO;
import com.mateus.scontrinoapi.dto.CategoryResponseDTO;
import com.mateus.scontrinoapi.infra.components.AuthUtil;
import com.mateus.scontrinoapi.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@Tag(name = "Categorias")
public class CategoryController {

    private final CategoryService service;
    private final AuthUtil authUtil;

    public CategoryController(CategoryService service, AuthUtil authUtil) {
        this.service = service;
        this.authUtil = authUtil;
    }

    @Operation(summary = "Lista todas as categorias da aplicação")
    @GetMapping("/general")
    public ResponseEntity<List<CategoryResponseDTO>> listAll() {
        List<CategoryResponseDTO> categoryList = this.service.listAll();

        return ResponseEntity.ok(categoryList);
    }

    @Operation(summary = "Lista todas as categorias do usuário autenticado")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listAllFromUser() {
        String email = this.authUtil.getEmail();

        List<CategoryResponseDTO> categoryList = this.service.listAllFromUser(email);

        return ResponseEntity.ok(categoryList);
    }

    @Operation(summary = "Cria uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<>(this.service.create(data, email), HttpStatus.CREATED);
    }

    @Operation(summary = "Apaga uma categoria pelo seu id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita uma categoria pelo seu id")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody CategoryDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<>(
                this.service.update(id, data, email),
                HttpStatus.ACCEPTED
        );
    }

}
