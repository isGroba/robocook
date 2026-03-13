package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.IngredientCreateDTO;
import com.cocina.robocook.dto.IngredientDTO;
import com.cocina.robocook.dto.IngredientUpdateDTO;
import com.cocina.robocook.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDTO toDTO(Ingredient ingredient){
        if (ingredient == null)
            return null;

        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .calories(ingredient.getCalories())
                .alternative(ingredient.getAlternative())
                .build();
    }

    public Ingredient toEntity(IngredientCreateDTO createDTO){
        if (createDTO == null)
            return null;

        Ingredient ingredient = new Ingredient();
        ingredient.setName(createDTO.getName());
        ingredient.setAlternative(createDTO.getAlternative());
        ingredient.setCalories(createDTO.getCalories());

        return ingredient;
    }

    public void updateEntity(IngredientUpdateDTO updateDTO, Ingredient ingredient){
        if (updateDTO == null)
            return;

        if (updateDTO.getName() != null)
            ingredient.setName(updateDTO.getName());
        if(updateDTO.getAlternative() != null)
            ingredient.setAlternative(updateDTO.getAlternative());
        if ((updateDTO.getCalories() != ingredient.getCalories()) && (updateDTO.getCalories() > 0))
            ingredient.setCalories(updateDTO.getCalories());

    }
}
