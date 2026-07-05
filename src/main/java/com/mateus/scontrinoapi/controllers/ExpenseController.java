package com.mateus.scontrinoapi.controllers;

import com.mateus.scontrinoapi.dto.ExpenseDTO;
import com.mateus.scontrinoapi.dto.ExpenseResponseDTO;
import com.mateus.scontrinoapi.infra.components.AuthUtil;
import com.mateus.scontrinoapi.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    private final AuthUtil authUtil;
    private final ExpenseService service;

    public ExpenseController(ExpenseService service, AuthUtil authUtil) {
        this.service = service;
        this.authUtil = authUtil;
    }

    @GetMapping("/general")
    public ResponseEntity<List<ExpenseResponseDTO>> listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> listAllFromUser() {
        String email = this.authUtil.getEmail();

        return ResponseEntity.ok(this.service.listAllFromUser(email));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@RequestBody ExpenseDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<ExpenseResponseDTO>(
                this.service.create(data, email),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> update(@PathVariable Long id, @RequestBody ExpenseDTO data) {
        String email = this.authUtil.getEmail();

        return new ResponseEntity<ExpenseResponseDTO>(
                this.service.update(id, data, email),
                HttpStatus.ACCEPTED
        );
    }



}
