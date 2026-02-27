package com.cocina.robocook.service;

import com.cocina.robocook.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe findById(Long id);

    Recipe save(Recipe recipe);

    void deleteById(Long id);
}
