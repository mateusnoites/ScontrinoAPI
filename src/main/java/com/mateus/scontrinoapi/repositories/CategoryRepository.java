package com.mateus.scontrinoapi.repositories;

import com.mateus.scontrinoapi.entities.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserEmail(String userEmail);
    boolean existsByNameAndUserId(String name, Long userId);
}
