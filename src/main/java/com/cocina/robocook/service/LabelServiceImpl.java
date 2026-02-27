package com.cocina.robocook.service;

import com.cocina.robocook.dao.LabelRepository;
import com.cocina.robocook.entity.Label;
import com.cocina.robocook.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LabelServiceImpl implements LabelService{
    private LabelRepository repository;

    @Autowired
    public LabelServiceImpl(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Label> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public Label findById(Long id) {
        Label theLabel = null;

        Optional<Label> result = repository.findById(id);

        if(result.isPresent()){
            theLabel = result.get();
        }else{
            throw new RuntimeException("Non se puido atopar a etiqueta para o id: " + id);
        }

        return theLabel;
    }

    @Override
    public Label save(Label label) {
        return repository.save(label);
    }

    @Override
    public void deleteById(Long id) {
        Label label = null;
        Optional<Label> result = repository.findById(id);
        if(result.isPresent()){
            label = result.get();
            Set<Recipe> recipes = label.getRecipes();
            for(Recipe recipe: recipes){
                recipe.getLabels().remove(label);
            }
            repository.deleteById(id);
        }
    }

}
