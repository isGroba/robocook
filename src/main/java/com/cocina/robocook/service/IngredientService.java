package com.cocina.robocook.service;

import com.cocina.robocook.dto.IngredientCreateDTO;
import com.cocina.robocook.dto.IngredientDTO;
import com.cocina.robocook.dto.IngredientUpdateDTO;

import java.util.List;

public interface IngredientService {

    List<IngredientDTO> findAll();

    IngredientDTO findById(Long id);

    List<IngredientDTO> findByNameContaining(String query);

    IngredientDTO create(IngredientCreateDTO createDTO);

    IngredientDTO update(Long id, IngredientUpdateDTO updateDTO);

    void deleteById(Long id);
}
