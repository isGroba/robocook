package com.cocina.robocook.service;

import com.cocina.robocook.dto.CategoryCreateDTO;
import com.cocina.robocook.dto.CategoryDTO;
import com.cocina.robocook.dto.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    List<CategoryDTO> findByNameContaining(String query);

    CategoryDTO create(CategoryCreateDTO createDTO);

    CategoryDTO update(Long id, CategoryUpdateDTO updateDTO);

    void deleteById(Long id);

}
