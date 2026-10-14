package com.cocina.robocook.repository;

import com.cocina.robocook.entity.Ingredient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

  List<Ingredient> findAllByOrderByNameAsc();

  List<Ingredient> findByNameContaining(String query);
}
