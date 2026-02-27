package com.cocina.robocook.service;

import com.cocina.robocook.dao.CategoryRepository;
import com.cocina.robocook.entity.Category;
import com.cocina.robocook.entity.Recipe;
import com.cocina.robocook.entity.RecipeIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public Category findById(Long id) {
        Category theCategory = null;

        Optional<Category> result = repository.findById(id);

        if(result.isPresent()){
            theCategory = result.get();
        }else{
            throw new RuntimeException("Non se puido atopar a categoría para o id: " + id);
        }

        return theCategory;
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = null;
        Optional<Category> result = repository.findById(id);
        if(result.isPresent()){
            category = result.get();
            Set<Recipe> recipes = category.getRecipes();
            for(Recipe recipe: recipes){
                recipe.getCategories().remove(category);
            }
            repository.deleteById(id);
        }
    }
}
