package com.cocina.robocook.service;

import com.cocina.robocook.dto.IngredientCreateDTO;
import com.cocina.robocook.dto.IngredientDTO;
import com.cocina.robocook.dto.IngredientUpdateDTO;
import com.cocina.robocook.mapper.IngredientMapper;
import com.cocina.robocook.repository.IngredientRepository;
import com.cocina.robocook.repository.RecipeIngredientRepository;
import com.cocina.robocook.entity.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.cocina.robocook.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository repository;
    private final RecipeIngredientRepository riRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() {
        log.debug("Get order list by ingredient name");

        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(ingredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDTO findById(Long id) {
        log.debug("Finding Ingredient with id: {}", id);

        Ingredient result = repository.findById(id).orElseThrow(()->{
            log.error("Ingredient not found: {}", id);
            return new ResourceNotFoundException("Ingredient NOT FOUND! id: " + id);
        });

        return ingredientMapper.toDTO(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> findByNameContaining(String query) {
        log.debug("Finding ingredients that contains {}", query);

        if(null == query || query.trim().isEmpty()){
            log.warn("Parameter is empty");
            return List.of();
        }

        return repository.findByNameContaining(query)
                .stream()
                .map(ingredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IngredientDTO create(IngredientCreateDTO createDTO) {
        log.debug("Creating new ingredient {}", createDTO.getName());

        Ingredient ingredient = ingredientMapper.toEntity(createDTO);
        Ingredient savedIngredient = repository.save(ingredient);
        log.info("Created new ingredient with ID: {}", savedIngredient.getId());

        return ingredientMapper.toDTO(savedIngredient);
    }

    @Override
    @Transactional
    public IngredientDTO update(Long id, IngredientUpdateDTO updateDTO) {
        log.debug("Updating ingredients with ID {}", id);

        Ingredient ingredient = repository.findById(id).orElseThrow(()->{
           log.debug("Ingredient Not found with ID: {}", id);
           return new ResourceNotFoundException("Ingredient Not found with ID: " + id);
        });

        ingredientMapper.updateEntity(updateDTO, ingredient);

        Ingredient updatedIngredient = repository.save(ingredient);
        log.info("Updated ingredient with ID: {}", updatedIngredient.getId());

        return ingredientMapper.toDTO(updatedIngredient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting ingredients with ID {}", id);

        if(!repository.existsById(id)){
            log.debug("Ingredient not found with ID: {}", id);
            throw new ResourceNotFoundException("Ingredient not found with ID: " + id);
        }

        if(riRepository.existsByIngredientId(id))
            riRepository.deleteByIngredientId(id);

        repository.deleteById(id);
    }
}
