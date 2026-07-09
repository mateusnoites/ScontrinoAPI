package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.ExpenseDTO;
import com.mateus.scontrinoapi.dto.ExpenseResponseDTO;
import com.mateus.scontrinoapi.infra.components.AuthUtil;
import com.mateus.scontrinoapi.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expense")
@Tag(name = "Gastos")
public class ExpenseController {

    private final AuthUtil authUtil;
    private final ExpenseService service;

    public ExpenseController(ExpenseService service, AuthUtil authUtil) {
        this.service = service;
        this.authUtil = authUtil;
    }

    @Operation(summary = "Lista todos os gastos cadastrados na aplicação")
    @GetMapping("/general")
    public ResponseEntity<List<ExpenseResponseDTO>> listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @Operation(summary = "Lista todos os gastos do usuário")
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> listAllFromUser(
            @RequestParam(name = "category", required = false, defaultValue = "") String categoryName
    ) {
        String email = this.authUtil.getEmail();
        List<ExpenseResponseDTO> response;

        if (!categoryName.isBlank()) response = this.service.listAllFromUser(email, categoryName);
        else response = this.service.listAllFromUser(email);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cria um novo gasto")
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@RequestBody ExpenseDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<>(
                this.service.create(data, email),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Apaga um gasto pelo seu id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita um gasto pelo seu id")
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> update(@PathVariable Long id, @RequestBody ExpenseDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<>(
                this.service.update(id, data, email),
                HttpStatus.ACCEPTED
        );
    }

}
