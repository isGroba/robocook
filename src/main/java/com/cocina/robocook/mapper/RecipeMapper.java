package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.RecipeCreateDTO;
import com.cocina.robocook.dto.RecipeDTO;
import com.cocina.robocook.dto.RecipeUpdateDTO;
import com.cocina.robocook.entity.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecipeMapper {

    private final CategoryMapper categoryMapper;
    private final LabelMapper labelMapper;
    private final StepMapper stepMapper;
    private final RecipeIngredientMapper ingredientMapper;

    public RecipeDTO toDTO(Recipe recipe){

        if (null == recipe)
            return null;

        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .preparationTime(recipe.getPreparationTime())
                .difficulty(recipe.getDifficulty())
                .season(recipe.getSeason())
                .healthyScore(recipe.getHealthyScore())
                .tasteScore(recipe.getTasteScore())
                .portions(recipe.getPortions())
                .calories(recipe.getCalories())
                .categories(null != recipe.getCategories() ? recipe.getCategories().stream().map(categoryMapper::toDTO).collect(Collectors.toList()):null)
                .labels(null != recipe.getLabels() ? recipe.getLabels().stream().map(labelMapper::toDTO).collect(Collectors.toList()):null)
                .steps(null != recipe.getSteps() ? recipe.getSteps().stream().map(stepMapper::toDTO).collect(Collectors.toList()):null)
                .recipeIngredients(null != recipe.getRecipeIngredients() ? recipe.getRecipeIngredients().stream().map(ingredientMapper::toDTO).collect(Collectors.toList()):null)
                .build();

    }

    public Recipe toEntity(RecipeCreateDTO createDTO){

        if(null == createDTO)
            return null;

        Recipe recipe = new Recipe();
        recipe.setName(createDTO.getName());
        recipe.setDescription(createDTO.getDescription());
        recipe.setPreparationTime(createDTO.getPreparationTime());
        recipe.setDifficulty(createDTO.getDifficulty());
        recipe.setSeason(createDTO.getSeason());
        recipe.setHealthyScore(createDTO.getHealthyScore());
        recipe.setTasteScore(createDTO.getTasteScore());
        recipe.setPortions(createDTO.getPortions());
        recipe.setCalories(createDTO.getCalories());

        return recipe;
    }

    public void updateEntity(RecipeUpdateDTO updateDTO, Recipe recipe){

        if (null == updateDTO)
            return;

        if (updateDTO.getName() != null)
            recipe.setName(updateDTO.getName());

        if (updateDTO.getDescription() != null)
            recipe.setDescription(updateDTO.getDescription());

        if (updateDTO.getPreparationTime() != null)
            recipe.setPreparationTime(updateDTO.getPreparationTime());

        if (updateDTO.getDifficulty() != null)
            recipe.setDifficulty(updateDTO.getDifficulty());

        if (updateDTO.getSeason() != null)
            recipe.setSeason(updateDTO.getSeason());

        if (updateDTO.getHealthyScore() != null)
            recipe.setHealthyScore(updateDTO.getHealthyScore());

        if (updateDTO.getTasteScore() != null)
            recipe.setTasteScore(updateDTO.getTasteScore());

        if (updateDTO.getPortions() != null)
            recipe.setPortions(updateDTO.getPortions());

        if (updateDTO.getCalories() != null)
            recipe.setCalories(updateDTO.getCalories());

    }

}
