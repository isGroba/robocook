package com.cocina.robocook.service;

import com.cocina.robocook.dto.*;

import java.util.List;

public interface RecipeService {

    List<RecipeDTO> findAll();

    PageResponseDTO<RecipeDTO> findAllPagination(int page, int size, String sortBy, String sortDirection);

    RecipeDTO findById(Long id);

    List<RecipeDTO> findByFilters(RecipeFilterDTO filterDTO);

    List<RecipeDTO> findByNameContaining(String query);

    RecipeDTO create(RecipeCreateDTO createDTO);

    RecipeDTO update(Long id, RecipeUpdateDTO updateDTO);

    RecipeDTO createComplete(RecipeDTO recipeDTO);

    RecipeDTO updateComplete(RecipeDTO recipeDTO);

    void deleteById(Long id);
}
