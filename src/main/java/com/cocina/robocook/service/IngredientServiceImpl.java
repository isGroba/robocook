package com.cocina.robocook.service;

import com.cocina.robocook.dao.IngredientRepository;
import com.cocina.robocook.dao.RecipeIngredientRepository;
import com.cocina.robocook.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

    private IngredientRepository repository;
    private RecipeIngredientRepository riRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository repository, RecipeIngredientRepository riRepository) {
        this.repository = repository;
        this.riRepository = riRepository;
    }

    @Override
    public List<Ingredient> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public Ingredient findById(Long id) {
        Ingredient ingredient = null;
        Optional<Ingredient> result = repository.findById(id);

        if(result.isPresent()){
            ingredient = result.get();
        }else{
            throw new RuntimeException("Non se puido recuperar o ingredente con id: " + id);
        }

        return ingredient;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        riRepository.deleteByIngredientId(id);
        repository.deleteById(id);
    }
}
