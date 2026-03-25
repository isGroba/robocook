package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.RecipeSimpleDTO;
import com.cocina.robocook.entity.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeSimpleMapper {

    public RecipeSimpleDTO toDTO(Recipe recipe){
        if(null == recipe)
            return null;

        return RecipeSimpleDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .difficulty(null != recipe.getDifficulty() ? recipe.getDifficulty(): null)
                .build();
    }
}
