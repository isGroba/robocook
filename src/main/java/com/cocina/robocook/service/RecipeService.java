package com.cocina.robocook.service;

import com.cocina.robocook.dto.RecipeCreateDTO;
import com.cocina.robocook.dto.RecipeDTO;
import com.cocina.robocook.dto.RecipeSimpleDTO;
import com.cocina.robocook.dto.RecipeUpdateDTO;

import java.util.List;

public interface RecipeService {

    List<RecipeDTO> findAll();

    List<RecipeSimpleDTO> findAllSimple();

    RecipeDTO findById(Long id);

    List<RecipeDTO> findByNameContaining(String query);

    RecipeDTO create(RecipeCreateDTO createDTO);

    RecipeDTO update(Long id, RecipeUpdateDTO updateDTO);

    RecipeDTO createComplete(RecipeDTO recipeDTO);

    RecipeDTO updateComplete(RecipeDTO recipeDTO);

    void deleteById(Long id);
}
