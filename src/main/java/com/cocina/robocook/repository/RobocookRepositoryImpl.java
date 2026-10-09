package com.cocina.robocook.repository;

import com.cocina.robocook.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

        return query.getSingleResult();
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

        return query.getSingleResult();
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

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public Recipe updateRecipe(Recipe tempRecipe) {
        return entityManager.merge(tempRecipe);
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
    public Label findLabelAndRecipesById(Long id) {
        TypedQuery<Label> query = entityManager.createQuery("select distinct l from Label l " +
                                            "LEFT JOIN FETCH l.recipes where l.id= :dataId", Label.class);
        query.setParameter("dataId", id);

        return query.getSingleResult();
    }

    @Override
    public Step findStepById(Long id) {
        return entityManager.find(Step.class, id);
    }

    @Override
    @Transactional
    public Step updateStep(Step tempStep) {
        return entityManager.merge(tempStep);
    }

    @Override
    @Transactional
    public void deleteStepById(Long id) {
        Step tempStep = entityManager.find(Step.class, id);
        entityManager.remove(tempStep);
    }


}
