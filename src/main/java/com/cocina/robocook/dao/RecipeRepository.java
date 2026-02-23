package com.cocina.robocook.dao;

import com.cocina.robocook.entity.*;
import org.springframework.data.jpa.repository.EntityGraph;

public interface RecipeRepository {

    void saveRecipe(Recipe tempReceita);

    Recipe findRecipeById(Long id);

    Recipe findRecipeAndIngredientsById(Long id);

    Recipe findRecipeCompleteById(Long id);

    void updateRecipe(Recipe tempReceita);

    void deleteRecipeById(Long id);

    Ingredient findIngredientById(Long id);

    //CATEGORY
    Category findCategoryAndRecipesById(Long id);

    void deleteCategoryById(Long id);

    // LABEL
    Label findLabelAndRecipesById(Long id);

    void deleteLabelById(Long id);

    // methods for StepEntity class
    Step findStepById(Long id);

    void updateStep(Step tempStep);

    void deleteStepById(Long id);

}
