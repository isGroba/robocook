package com.cocina.robocook.dao;

import com.cocina.robocook.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByOrderByNameAsc();

}
