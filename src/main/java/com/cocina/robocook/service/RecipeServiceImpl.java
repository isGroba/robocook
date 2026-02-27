package com.cocina.robocook.service;

import com.cocina.robocook.dao.RecipeRepository;
import com.cocina.robocook.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService{

    private RecipeRepository repository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> findAll() {
        return repository.findAllByOrderByNameAsc();

    }

    @Override
    public Recipe findById(Long id) {
        Recipe recipe = null;

        Optional<Recipe> result = repository.findById(id);

        if(result.isPresent()){
            recipe = result.get();
        }else{
            throw new RuntimeException("Non se puido atopar a receita para o id: " + id);
        }

        return recipe;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    @Override
    public void deleteById(Long id) {
        /*
        Recipe recipe = null;
        Optional<Recipe> result = repository.findById(id);
        if(result.isPresent()){
            recipe = result.get();
            recipe.getLabels();
        }
        */
        repository.deleteById(id);
    }
}
