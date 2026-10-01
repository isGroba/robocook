package com.cocina.robocook.controller.api;

import com.cocina.robocook.dto.RecipeDTO;
import com.cocina.robocook.dto.RecipeSimpleDTO;
import com.cocina.robocook.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Recipes", description = "This section manage the recipes in general")
public class RecipeApiController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeSimpleDTO>> getAllRecipes(){
        log.info("GET /api/v1/recipes - Get all recipes");

        List<RecipeSimpleDTO> recipeDTOS = recipeService.findAllSimple();

        return ResponseEntity.ok(recipeDTOS);
    }

}
