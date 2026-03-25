package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.CategoryCreateDTO;
import com.cocina.robocook.dto.CategoryDTO;
import com.cocina.robocook.dto.CategoryUpdateDTO;
import com.cocina.robocook.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CategoryMapper {

    private final RecipeSimpleMapper recipeSimpleMapper;

    public CategoryDTO toDTO(Category category){
        if(null == category)
            return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .recipes(null != category.getRecipes()?
                            category.getRecipes().stream()
                                    .map(recipeSimpleMapper::toDTO)
                                    .collect(Collectors.toList())
                            : null)
                .build();
    }

    public Category toEntity(CategoryCreateDTO createDTO){
        if(null == createDTO)
            return null;

        Category category = new Category();
        category.setName(createDTO.getName());

        return category;
    }

    public void updateEntity(CategoryUpdateDTO updateDTO, Category category){
        if (updateDTO == null)
            return;

        if (updateDTO.getName() != null)
            category.setName(updateDTO.getName());
    }

}
