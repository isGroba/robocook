package com.cocina.robocook.service;

import com.cocina.robocook.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient findById(Long id);

    List<Ingredient> findByNameContaining(String query);

    Ingredient save(Ingredient ingredient);

    void deleteById(Long id);
}
