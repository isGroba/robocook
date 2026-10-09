package com.cocina.robocook.repository;

import com.cocina.robocook.entity.*;

public interface RobocookRepository {

    void saveRecipe(Recipe tempReceita);

    Recipe findRecipeById(Long id);

    Recipe findRecipeAndIngredientsById(Long id);

    Recipe findRecipeCompleteById(Long id);

    Recipe updateRecipe(Recipe tempReceita);

    Ingredient findIngredientById(Long id);

    // CATEGORY
    Category findCategoryAndRecipesById(Long id);

    // LABEL
    Label findLabelAndRecipesById(Long id);

    // methods for Step class
    Step findStepById(Long id);

    Step updateStep(Step tempStep);

    void deleteStepById(Long id);

}
