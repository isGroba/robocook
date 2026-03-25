package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.entity.Ingredient;
import com.cocina.robocook.entity.RecipeIngredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeIngredientMapper {

    private final IngredientMapper ingredientMapper;
    private final RecipeMapper recipeMapper;

    public RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient){

        if (null == recipeIngredient)
            return null;

        return RecipeIngredientDTO.builder()
                .id(recipeIngredient.getId())
                .ingredient(ingredientMapper.toDTO(recipeIngredient.getIngredient()))
                .unit(recipeIngredient.getUnit())
                .amount(recipeIngredient.getAmount())
                .optional(recipeIngredient.isOptional())
                .build();
    }

    public RecipeIngredient toEntity(RecipeIngredientCreateDTO createDTO, Ingredient ingredient){

        if (null == createDTO)
            return null;

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(createDTO.getId());
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setUnit(createDTO.getUnit());
        recipeIngredient.setAmount(recipeIngredient.getAmount());
        recipeIngredient.setOptional(createDTO.isOptional());

        return recipeIngredient;
    }

    public void updateEntity(RecipeIngredientUpdateDTO updateDTO, RecipeIngredient recipeIngredient){
        if (null == updateDTO)
            return;

        if(updateDTO.getAmount() != null)
            recipeIngredient.setAmount(updateDTO.getAmount());
        if (updateDTO.getUnit() != null)
            recipeIngredient.setUnit(updateDTO.getUnit());
        if(updateDTO.getOptional() != null)
            recipeIngredient.setOptional(updateDTO.getOptional());


    }
}
