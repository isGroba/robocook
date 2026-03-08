package com.cocina.robocook.repository;

import com.cocina.robocook.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    void deleteByIngredientId(Long id);
}
