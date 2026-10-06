package com.cocina.robocook.service;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.entity.*;
import com.cocina.robocook.exception.ResourceNotFoundException;
import com.cocina.robocook.mapper.RecipeMapper;
import com.cocina.robocook.repository.CategoryRepository;
import com.cocina.robocook.repository.IngredientRepository;
import com.cocina.robocook.repository.LabelRepository;
import com.cocina.robocook.repository.RecipeRepository;
import com.cocina.robocook.utils.RecipeSorter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository repository;
    private final RecipeMapper recipeMapper;

    private final CategoryRepository categoryRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public List<RecipeDTO> findAll() {
        log.debug("Get order list by recipe name");

        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO findById(Long id) {
        log.debug("Finding Recipe with id: {}", id);

        Recipe recipe = repository.findById(id)
                .orElseThrow(()->{
                    log.error("Recipe not found with ID: {}", id);
                    return new ResourceNotFoundException("Recipe not found with ID: " + id);
                });
        return recipeMapper.toDTO(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> findByFilters(RecipeFilterDTO filterDTO) {
        Season season = filterDTO.getSeason() !=null ? filterDTO.getSeason() : null;
        Difficulty difficulty = filterDTO.getDifficulty() != null ? filterDTO.getDifficulty() : null;
        List<Long> labels = filterDTO.getLabelIds() != null && !filterDTO.getLabelIds().isEmpty()? filterDTO.getLabelIds() : null;
        List<Long> categories = filterDTO.getCategoryIds()!= null && !filterDTO.getCategoryIds().isEmpty()? filterDTO.getCategoryIds() : null;
        Integer preparationTime = filterDTO.getPreparationTime() != null ? filterDTO.getPreparationTime() : null;

        List<Recipe> result = repository.findByFilters(
                filterDTO.getName(),
                difficulty,
                season,
                categories,
                labels,
                filterDTO.getMinHealthyScore(),
                filterDTO.getMaxHealthyScore(),
                filterDTO.getMinTasteScore(),
                filterDTO.getMaxTasteScore(),
                preparationTime);

        result = RecipeSorter.sortRecipes(result, filterDTO.getSortBy(), filterDTO.getSortDirection());

        log.info("Found {} recipes matching filters", result.size());
        return result.stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> findByNameContaining(String query) {
        log.debug("Finding recipes that contains: {}", query);

        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        return repository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO create(RecipeCreateDTO createDTO) {
        log.debug("Creating new recipe {}}", createDTO.getName());

        Recipe savedRecipe = repository.save(recipeMapper.toEntity(createDTO));
        log.info("Created new recipe with ID: {}", savedRecipe.getId());

        return recipeMapper.toDTO(savedRecipe);
    }

    @Override
    public RecipeDTO update(Long id, RecipeUpdateDTO updateDTO) {
        log.debug("Updating recipe {}", updateDTO.getName());

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Recipe not found with ID: {}", id);
                    return new ResourceNotFoundException("Recipe not found with ID: " + id);
                });

        recipeMapper.updateEntity(updateDTO, recipe);
        Recipe updatedRecipe = repository.save(recipe);

        log.info("Updated recipe with ID: {}", updatedRecipe.getId());
        return recipeMapper.toDTO(updatedRecipe);
    }

    @Override
    public RecipeDTO createComplete(RecipeDTO recipeDTO) {
        log.debug("Creating new complete recipe {}", recipeDTO.getName());

        try{
            // create recipe
            Recipe recipe = recipeMapper.toEntity(recipeDTO);

            // add categories
            if(null != recipeDTO.getCategories() && !recipeDTO.getCategories().isEmpty()){
                log.debug("Adding {} labels", recipeDTO.getCategories().size());

                for(CategorySimpleDTO categorySimpleDTO : recipeDTO.getCategories()){
                    Category category = categoryRepository.findById(categorySimpleDTO.getId())
                            .orElseThrow(() -> {
                                log.error("Category not found with ID: {}", categorySimpleDTO.getId());
                                return new ResourceNotFoundException("Category not found with ID: " + categorySimpleDTO.getId());
                            });
                    recipe.addCategory(category);
                }
            }

            // add Labels
            if (null != recipeDTO.getLabels() && !recipeDTO.getLabels().isEmpty()) {
                log.debug("Adding {} labels", recipeDTO.getLabels().size());

                for (LabelSimpleDTO labelSimpleDTO : recipeDTO.getLabels()) {
                    Label label = labelRepository.findById(labelSimpleDTO.getId())
                            .orElseThrow(() -> {
                                log.error("Label not found with ID: {}", labelSimpleDTO.getId());
                                return new ResourceNotFoundException("Label not found with ID: " + labelSimpleDTO.getId());
                            });
                    recipe.addLabel(label);
                }
            }

            // add Steps
            if (null != recipeDTO.getSteps() && !recipeDTO.getSteps().isEmpty()) {
                log.debug("Adding {} steps", recipeDTO.getSteps().size());

                for (StepDTO stepDTO : recipeDTO.getSteps()) {
                    Step step = new Step();
                    step.setOrderNumber(stepDTO.getOrderNumber());
                    step.setDescription(stepDTO.getDescription());
                    recipe.addStep(step);
                }
            }

            // add Ingredients
            if (null != recipeDTO.getRecipeIngredients() && !recipeDTO.getRecipeIngredients().isEmpty()) {
                log.debug("Adding {} ingredients", recipeDTO.getRecipeIngredients().size());

                for (RecipeIngredientDTO recipeIngredientDTO : recipeDTO.getRecipeIngredients()) {
                    Ingredient ingredient = ingredientRepository.findById(recipeIngredientDTO.getIngredient().getId())
                            .orElseThrow(() -> {
                                log.error("Ingredient not found with ID: {}", recipeIngredientDTO.getIngredient().getId());
                                return new ResourceNotFoundException("Ingredient not found with ID: " + recipeIngredientDTO.getIngredient().getId());
                            });

                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(recipe);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setAmount(recipeIngredientDTO.getAmount());
                    recipeIngredient.setUnit(recipeIngredientDTO.getUnit());
                    recipeIngredient.setOptional(recipeIngredientDTO.isOptional());

                    recipe.addRecipeIngredient(recipeIngredient);
                }
            }

            // save complete recipe
            log.info("Saving complete recipe in database");
            Recipe savedRecipe = repository.save(recipe);

            log.info("Complete recipe created in DB with ID: {}", savedRecipe.getId());
            return recipeMapper.toDTO(savedRecipe);

        }catch (ResourceNotFoundException ex){
            log.error("Error validation to create complete recipe: {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Error unexpected to create complete recipe: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error to create recipe: " + ex.getMessage(), ex);
        }

    }

    @Override
    public RecipeDTO updateComplete(RecipeDTO recipeDTO) {
        log.debug("Updating complete recipe {}", recipeDTO.getName());

        try{
            // update recipe
            Recipe recipe = repository.findById(recipeDTO.getId())
                    .orElseThrow(() -> {
                        log.error("Recipe not found with ID: {}", recipeDTO.getId());
                        return new ResourceNotFoundException("Recipe not found with ID: " + recipeDTO.getId());
                    });

            RecipeUpdateDTO recipeUpdateDTO = recipeMapper.toUpdateDTO(recipeDTO);
            recipeMapper.updateEntity(recipeUpdateDTO, recipe);

            // update labels
            if(null != recipeDTO.getLabels() && !recipeDTO.getLabels().isEmpty()) {
                log.debug("Updating labels. Olds {}, News{}", recipe.getLabels().size(), recipeDTO.getLabels().size());

                if (!recipe.getLabels().isEmpty())
                    recipe.getLabels().clear();

                for (LabelSimpleDTO labelSimpleDTO : recipeDTO.getLabels()) {
                    Label label = labelRepository.findById(labelSimpleDTO.getId())
                            .orElseThrow(() -> {
                                log.error("Label not found with ID: {}", labelSimpleDTO.getId());
                                return new ResourceNotFoundException("Label not found with ID: " + labelSimpleDTO.getId());
                            });
                    recipe.addLabel(label);
                }
            }

            // update Categories
            if(null != recipeDTO.getCategories() && !recipeDTO.getCategories().isEmpty()) {
                log.debug("Updating categories. Olds {}, News{}", recipe.getCategories().size(), recipeDTO.getCategories().size());

                if (!recipe.getCategories().isEmpty())
                    recipe.getCategories().clear();

                for (CategorySimpleDTO categorySimpleDTO : recipeDTO.getCategories()) {
                    Category category = categoryRepository.findById(categorySimpleDTO.getId())
                            .orElseThrow(() -> {
                                log.error("Category not found with ID: {}", categorySimpleDTO.getId());
                                return new ResourceNotFoundException("Category not found with ID: " + categorySimpleDTO.getId());
                            });
                    recipe.addCategory(category);
                }
            }
            // update Steps
            if(null != recipeDTO.getSteps() && !recipeDTO.getSteps().isEmpty()) {
                log.debug("Updating steps. Olds {}, News{}", recipe.getSteps().size(), recipeDTO.getSteps().size());

                if (!recipe.getSteps().isEmpty())
                    recipe.getSteps().clear();

                for (StepDTO stepDTO : recipeDTO.getSteps()) {
                    Step step = new Step();
                    step.setOrderNumber(stepDTO.getOrderNumber());
                    step.setDescription(stepDTO.getDescription());
                    recipe.addStep(step);
                }
            }
            // update Ingredients
            if (recipeDTO.getRecipeIngredients() != null) {
                log.debug("Updating ingredients. Olds: {}, News: {}",
                        recipe.getRecipeIngredients().size(), recipeDTO.getRecipeIngredients().size());

                if (!recipe.getRecipeIngredients().isEmpty())
                    recipe.getRecipeIngredients().clear();

                for (RecipeIngredientDTO recipeIngredientDTO : recipeDTO.getRecipeIngredients()){
                    Ingredient ingredient = ingredientRepository.findById(recipeIngredientDTO.getIngredient().getId())
                            .orElseThrow(() -> {
                                log.error("Ingredient not found with ID: {}", recipeIngredientDTO.getIngredient().getId());
                                return new ResourceNotFoundException("Ingredient not found with ID: " + recipeIngredientDTO.getIngredient().getId());
                            });

                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(recipe);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setAmount(recipeIngredientDTO.getAmount());
                    recipeIngredient.setUnit(recipeIngredientDTO.getUnit());
                    recipeIngredient.setOptional(recipeIngredientDTO.isOptional());

                    recipe.addRecipeIngredient(recipeIngredient);
                }
            }

            Recipe updatedRecipe = repository.save(recipe);

            log.info("Updated recipe with ID: {}", updatedRecipe.getId());
            return recipeMapper.toDTO(updatedRecipe);

        }catch (ResourceNotFoundException ex){
            log.error("Error validation to create complete recipe: {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Error unexpected to create complete recipe: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error to create recipe: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting recipe with ID {}", id);

        if(repository.existsById(id)){
            log.debug("Recipe not found with ID: {}", id);
            throw new ResourceNotFoundException("Recipe not found with ID: " + id);
        }

        repository.deleteById(id);
    }

}
