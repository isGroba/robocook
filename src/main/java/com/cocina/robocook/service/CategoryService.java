package com.cocina.robocook.service;

import com.cocina.robocook.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    List<Category> findByNameContaining(String query);

    Category save(Category category);

    void deleteById(Long id);

}
