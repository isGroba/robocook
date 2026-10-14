package com.cocina.robocook.service;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.entity.*;
import com.cocina.robocook.exception.ResourceNotFoundException;
import com.cocina.robocook.mapper.RecipeMapper;
import com.cocina.robocook.repository.CategoryRepository;
import com.cocina.robocook.repository.IngredientRepository;
import com.cocina.robocook.repository.LabelRepository;
import com.cocina.robocook.repository.RecipeRepository;
import com.cocina.robocook.utils.HelperPagination;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository repository;
  private final RecipeMapper recipeMapper;

  private final CategoryRepository categoryRepository;
  private final LabelRepository labelRepository;
  private final IngredientRepository ingredientRepository;

  @Override
  public List<RecipeDTO> findAll() {
    log.debug("Get order list ");

    return repository.findAllByOrderByNameAsc().stream().map(recipeMapper::toDTO).toList();
  }

  @Override
  public PageResponseDTO<RecipeDTO> findAllPaginated(
      int page, int size, String sortBy, String sortDirection) {
    log.debug("Get order list by pagination");

    page = Math.max(0, page);
    size = HelperPagination.validatePageSize(size);
    sortBy = HelperPagination.normalizeSortBy(sortBy);

    Sort.Direction direction =
        "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Recipe> recipePage = repository.findAll(pageable);
    List<RecipeDTO> recipesDTO = recipePage.getContent().stream().map(recipeMapper::toDTO).toList();

    return PageResponseDTO.from(
        new org.springframework.data.domain.PageImpl<>(
            recipesDTO, pageable, recipePage.getTotalElements()));
  }

  @Override
  public RecipeDTO findById(Long id) {
    log.debug("Finding Recipe with id: {}", id);

    Recipe recipe = findRecipeEntityOrThrow(id);
    return recipeMapper.toDTO(recipe);
  }

  @Override
  public PageResponseDTO<RecipeDTO> findByFiltersPaginated(
      RecipeFilterDTO filterDTO, int page, int size, String sortBy, String sortDirection) {
    Season season = filterDTO.getSeason() != null ? filterDTO.getSeason() : null;
    Difficulty difficulty = filterDTO.getDifficulty() != null ? filterDTO.getDifficulty() : null;
    List<Long> labels =
        filterDTO.getLabelIds() != null && !filterDTO.getLabelIds().isEmpty()
            ? filterDTO.getLabelIds()
            : null;
    List<Long> categories =
        filterDTO.getCategoryIds() != null && !filterDTO.getCategoryIds().isEmpty()
            ? filterDTO.getCategoryIds()
            : null;
    Integer preparationTime =
        filterDTO.getPreparationTime() != null ? filterDTO.getPreparationTime() : null;

    page = Math.max(0, page);
    size = HelperPagination.validatePageSize(size);
    sortBy = HelperPagination.normalizeSortBy(sortBy);

    Sort.Direction direction =
        "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Recipe> recipePage =
        repository.findByFiltersPagination(
            filterDTO.getName(),
            difficulty,
            season,
            categories,
            labels,
            filterDTO.getMinHealthyScore(),
            filterDTO.getMaxHealthyScore(),
            filterDTO.getMinTasteScore(),
            filterDTO.getMaxTasteScore(),
            preparationTime,
            pageable);
    List<RecipeDTO> recipesDTO = recipePage.getContent().stream().map(recipeMapper::toDTO).toList();

    return PageResponseDTO.from(
        new org.springframework.data.domain.PageImpl<>(
            recipesDTO, pageable, recipePage.getTotalElements()));
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

    Recipe recipe = findRecipeEntityOrThrow(id);

    recipeMapper.updateEntity(updateDTO, recipe);
    Recipe updatedRecipe = repository.save(recipe);

    log.info("Updated recipe with ID: {}", updatedRecipe.getId());
    return recipeMapper.toDTO(updatedRecipe);
  }

  @Override
  public RecipeDTO createComplete(RecipeDTO recipeDTO) {
    log.debug("Creating new complete recipe {}", recipeDTO.getName());

    try {
      // create recipe
      Recipe recipe = recipeMapper.toEntity(recipeDTO);

      // add categories
      if (!CollectionUtils.isEmpty(recipeDTO.getCategories())) {
        log.debug("Adding {} labels", recipeDTO.getCategories().size());

        for (CategorySimpleDTO categorySimpleDTO : recipeDTO.getCategories()) {
          Category category =
              categoryRepository
                  .findById(categorySimpleDTO.getId())
                  .orElseThrow(
                      () -> {
                        log.error("Category not found with ID: {}", categorySimpleDTO.getId());
                        return new ResourceNotFoundException(
                            "Category not found with ID: " + categorySimpleDTO.getId());
                      });
          recipe.addCategory(category);
        }
      }

      // add Labels
      if (!CollectionUtils.isEmpty(recipeDTO.getLabels())) {
        log.debug("Adding {} labels", recipeDTO.getLabels().size());

        for (LabelSimpleDTO labelSimpleDTO : recipeDTO.getLabels()) {
          Label label =
              labelRepository
                  .findById(labelSimpleDTO.getId())
                  .orElseThrow(
                      () -> {
                        log.error("Label not found with ID: {}", labelSimpleDTO.getId());
                        return new ResourceNotFoundException(
                            "Label not found with ID: " + labelSimpleDTO.getId());
                      });
          recipe.addLabel(label);
        }
      }

      // add Steps
      if (!CollectionUtils.isEmpty(recipeDTO.getSteps())) {
        log.debug("Adding {} steps", recipeDTO.getSteps().size());

        for (StepDTO stepDTO : recipeDTO.getSteps()) {
          Step step = new Step();
          step.setOrderNumber(stepDTO.getOrderNumber());
          step.setDescription(stepDTO.getDescription());
          recipe.addStep(step);
        }
      }

      // add Ingredients
      if (!CollectionUtils.isEmpty(recipeDTO.getRecipeIngredients())) {
        log.debug("Adding {} ingredients", recipeDTO.getRecipeIngredients().size());

        for (RecipeIngredientDTO recipeIngredientDTO : recipeDTO.getRecipeIngredients()) {
          Ingredient ingredient =
              ingredientRepository
                  .findById(recipeIngredientDTO.getIngredient().getId())
                  .orElseThrow(
                      () -> {
                        log.error(
                            "Ingredient not found with ID: {}",
                            recipeIngredientDTO.getIngredient().getId());
                        return new ResourceNotFoundException(
                            "Ingredient not found with ID: "
                                + recipeIngredientDTO.getIngredient().getId());
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

    } catch (ResourceNotFoundException ex) {
      log.error("Error validation to create complete recipe: {}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("Error unexpected to create complete recipe: {}", ex.getMessage(), ex);
      throw new RuntimeException("Error to create recipe: " + ex.getMessage(), ex);
    }
  }

  @Override
  public RecipeDTO updateComplete(RecipeDTO recipeDTO) {
    log.debug("Updating complete recipe {}", recipeDTO.getName());

    try {
      // update recipe
      Recipe recipe = findRecipeEntityOrThrow(recipeDTO.getId());

      RecipeUpdateDTO recipeUpdateDTO = recipeMapper.toUpdateDTO(recipeDTO);
      recipeMapper.updateEntity(recipeUpdateDTO, recipe);

      // update labels
      if (!CollectionUtils.isEmpty( recipeDTO.getLabels())) {
        log.debug(
            "Updating labels. Olds {}, News{}",
            recipe.getLabels().size(),
            recipeDTO.getLabels().size());
        recipe.getLabels().clear();

        for (LabelSimpleDTO labelSimpleDTO : recipeDTO.getLabels()) {
          Label label =
              labelRepository
                  .findById(labelSimpleDTO.getId())
                  .orElseThrow(
                      () -> {
                        log.error("Label not found with ID: {}", labelSimpleDTO.getId());
                        return new ResourceNotFoundException(
                            "Label not found with ID: " + labelSimpleDTO.getId());
                      });
          recipe.addLabel(label);
        }
      }

      // update Categories
      if (!CollectionUtils.isEmpty(recipeDTO.getCategories())) {
        log.debug(
            "Updating categories. Olds {}, News{}",
            recipe.getCategories().size(),
            recipeDTO.getCategories().size());
        recipe.getCategories().clear();

        for (CategorySimpleDTO categorySimpleDTO : recipeDTO.getCategories()) {
          Category category =
              categoryRepository
                  .findById(categorySimpleDTO.getId())
                  .orElseThrow(
                      () -> {
                        log.error("Category not found with ID: {}", categorySimpleDTO.getId());
                        return new ResourceNotFoundException(
                            "Category not found with ID: " + categorySimpleDTO.getId());
                      });
          recipe.addCategory(category);
        }
      }
      // update Steps
      if (!CollectionUtils.isEmpty(recipeDTO.getSteps())) {
        log.debug(
            "Updating steps. Olds {}, News{}",
            recipe.getSteps().size(),
            recipeDTO.getSteps().size());
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
        log.debug(
            "Updating ingredients. Olds: {}, News: {}",
            recipe.getRecipeIngredients().size(),
            recipeDTO.getRecipeIngredients().size());
        recipe.getRecipeIngredients().clear();

        for (RecipeIngredientDTO recipeIngredientDTO : recipeDTO.getRecipeIngredients()) {
          Ingredient ingredient =
              ingredientRepository
                  .findById(recipeIngredientDTO.getIngredient().getId())
                  .orElseThrow(
                      () -> {
                        log.error(
                            "Ingredient not found with ID: {}",
                            recipeIngredientDTO.getIngredient().getId());
                        return new ResourceNotFoundException(
                            "Ingredient not found with ID: "
                                + recipeIngredientDTO.getIngredient().getId());
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

    } catch (ResourceNotFoundException ex) {
      log.error("Error validation to create complete recipe: {}", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error("Error unexpected to create complete recipe: {}", ex.getMessage(), ex);
      throw new RuntimeException("Error to create recipe: " + ex.getMessage(), ex);
    }
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting recipe with ID {}", id);

    if (repository.existsById(id)) {
      log.debug("Recipe not found with ID: {}", id);
      throw new ResourceNotFoundException("Recipe not found with ID: " + id);
    }

    repository.deleteById(id);
  }

// /////////////
// METHODS AUX
// /////////////

  private Recipe findRecipeEntityOrThrow(Long id) {
    return repository
            .findById(id)
            .orElseThrow(() -> {
              log.error("Recipe not found with ID: {}", id);
              return new ResourceNotFoundException("Recipe not found with ID: " + id);
            });
  }
}
