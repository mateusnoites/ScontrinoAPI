package com.mateus.scontrinoapi.infra.components;

import com.mateus.scontrinoapi.entities.Category.Category;
import com.mateus.scontrinoapi.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args){
        List<String> categorias = List.of(
                "Alimentação",
                "Moradia",
                "Transporte",
                "Saúde",
                "Educação",
                "Lazer",
                "Vestuário",
                "Assinaturas",
                "Viagens",
                "Investimentos"
        );

        categorias.forEach(name -> {
            if (!categoryRepository.existsByNameAndUserIsNull(name)) {
                Category newCategory = new Category();
                newCategory.setName(name);
                newCategory.setUser(null);
                categoryRepository.save(newCategory);
            }
        });
    }

}
