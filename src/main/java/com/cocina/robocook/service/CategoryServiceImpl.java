package com.cocina.robocook.service;

import com.cocina.robocook.dto.CategoryCreateDTO;
import com.cocina.robocook.dto.CategoryDTO;
import com.cocina.robocook.dto.CategoryUpdateDTO;
import com.cocina.robocook.exception.ResourceNotFoundException;
import com.cocina.robocook.mapper.CategoryMapper;
import com.cocina.robocook.repository.CategoryRepository;
import com.cocina.robocook.entity.Category;
import com.cocina.robocook.entity.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        log.debug("Get order list by category name");

        return repository.findAllByOrderByNameAsc()
                .stream().map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        log.debug("Finding Category with id: {}", id);

        Category result = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found: {}", id);
                    return new ResourceNotFoundException("Category NOT FOUND! id: " + id);
                });

        return categoryMapper.toDTO(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findByNameContaining(String query) {
        log.debug("Finding categories that contains {}", query);

        if(null == query || query.isEmpty())
            return List.of();

        return repository.findByNameContaining(query)
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO create(CategoryCreateDTO createDTO) {
        log.debug("Creating new category {}", createDTO.getName());

        Category category = categoryMapper.toEntity(createDTO);
        Category savedCategory = repository.save(category);
        log.info("Created new category with ID: {}", savedCategory.getId());

        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryUpdateDTO updateDTO) {
        log.debug("Updating categories with ID {}", id);

        Category result = repository.findById(id)
                .orElseThrow(()->{
                    log.debug("Category Not found with ID: {}", id);
                    return new ResourceNotFoundException("Category Not found with ID: " + id);
                });

        categoryMapper.updateEntity(updateDTO, result);
        Category updatedCategory = repository.save(result);
        log.info("Updated category with ID: {}", updatedCategory.getId());

        return categoryMapper.toDTO(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting categories with ID {}", id);

        Category result = repository.findById(id)
                .orElseThrow(()->{
                    log.error("Category not found with ID: {}", id);
                    return new ResourceNotFoundException("Category not found with ID: " + id);
                });

        Set<Recipe> recipes = result.getRecipes();
        for(Recipe recipe: recipes){
            recipe.getCategories().remove(result);
        }
        repository.deleteById(id);
    }
}
