package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.CategoryCreateDTO;
import com.cocina.robocook.dto.CategoryDTO;
import com.cocina.robocook.dto.CategoryUpdateDTO;
import com.cocina.robocook.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category){
        if(null == category)
            return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .recipes(category.getRecipes())
                .build();
    }

    public Category toEntity(CategoryCreateDTO createDTO){
        if(null == createDTO)
            return null;

        Category category = new Category();
        category.setName(createDTO.getName());
        category.setRecipes(createDTO.getRecipes());

        return category;
    }

    public void updateEntity(CategoryUpdateDTO updateDTO, Category category){
        if (updateDTO == null)
            return;

        if (updateDTO.getName() != null)
            category.setName(updateDTO.getName());
        if(updateDTO.getRecipes() != null)
            category.setRecipes(updateDTO.getRecipes());
    }

}
