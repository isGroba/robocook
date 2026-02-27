package com.cocina.robocook.dao;

import com.cocina.robocook.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public class RobocookRepositoryImpl implements RobocookRepository {

    private final EntityManager entityManager;

    public RobocookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveRecipe(Recipe tempRecipe) {
        entityManager.persist(tempRecipe);
    }

    @Override
    public Recipe findRecipeById(Long id) {

        TypedQuery<Recipe> query = entityManager.createQuery("select r from Recipe r join fetch r.steps where r.id = :valueId", Recipe.class);
        query.setParameter("valueId", id);

        Recipe recipe = query.getSingleResult();

        return recipe;
    }

    // mejor dividir esta consulta en varias para no tener múltiples JOIN
    // otra alternativa es utilizando JPA user @EntityGraph, también se puede utilizar con DAO custom, pero hay que crear usar @NamedEntityGraph en el repository de Recipe
    // no sé si esta consulta va a tener sentido en la app por eso no la evoluciono
    @Override
    public Recipe findRecipeAndIngredientsById(Long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("select distinct r " +
                "from Recipe r join fetch r.recipeIngredients ri " +
                "join fetch ri.ingredient " +
                "join fetch r.steps " +
                "where r.id = :data", Recipe.class);
        query.setParameter("data", id);

        Recipe recipe = query.getSingleResult();
        return recipe;
    }

    @Override
    public Recipe findRecipeCompleteById(Long id) {
        TypedQuery<Recipe> query = entityManager.createQuery("select distinct r " +
                "from Recipe r left join fetch r.recipeIngredients ri " +
                "left join fetch r.categories " +
                "left join fetch r.labels " +
                "left join fetch ri.ingredient " +
                "left join fetch r.steps " +
                "where r.id = :data", Recipe.class);
        query.setParameter("data", id);

        Recipe recipe = query.getSingleResult();
        return recipe;
    }

    @Override
    @Transactional
    public void updateRecipe(Recipe tempRecipe) {
        entityManager.merge(tempRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe tempRecipe = entityManager.find(Recipe.class, id);
        entityManager.remove(tempRecipe);
    }

    @Override
    public Ingredient findIngredientById(Long id) {
        return entityManager.find(Ingredient.class, id);
    }

    @Override
    public Category findCategoryAndRecipesById(Long id) {
        TypedQuery<Category> query = entityManager.createQuery("select distinct c from Category c " +
                "LEFT JOIN FETCH c.recipes where c.id= :dataId", Category.class);
        query.setParameter("dataId", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if(category != null){
            Set<Recipe> recipes = category.getRecipes();
            for(Recipe theRecipe: recipes){
                theRecipe.getCategories().remove(category);
            }
            entityManager.remove(category);
        }
    }

    @Override
    public Label findLabelAndRecipesById(Long id) {
        TypedQuery<Label> query = entityManager.createQuery("select distinct l from Label l " +
                                            "LEFT JOIN FETCH l.recipes where l.id= :dataId", Label.class);
        query.setParameter("dataId", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteLabelById(Long id) {
        Label theLabel = entityManager.find(Label.class, id);
        if(theLabel != null){
            Set<Recipe> recipes = theLabel.getRecipes();
            for(Recipe theRecipe: recipes){
                theRecipe.getLabels().remove(theLabel);
            }
            entityManager.remove(theLabel);
        }
    }

    @Override
    public Step findStepById(Long id) {
        return entityManager.find(Step.class, id);
    }

    @Override
    @Transactional
    public void updateStep(Step tempStep) {
        entityManager.merge(tempStep);
    }

    @Override
    @Transactional
    public void deleteStepById(Long id) {
        Step tempStep = entityManager.find(Step.class, id);
        entityManager.remove(tempStep);
    }


}
