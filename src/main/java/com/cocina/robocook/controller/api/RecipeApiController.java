package com.cocina.robocook.controller.api;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Recipes", description = "This section manage the complete recipes in general")
public class RecipeApiController {

    private final RecipeService recipeService;

    @Operation(
            summary = "Get recipe by ID",
            description = "Returns a specific recipe identified by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipe found",
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipe not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id){
        log.info("GET /api/v1/recipes/{} - Finding recipe", id);
        RecipeDTO recipeDTO = recipeService.findById(id);
        return ResponseEntity.ok(recipeDTO);
    }

    @Operation(
            summary = "Get all recipes",
            description = "Obtain all recipes with pagination"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of recipes successfully obtained",
            content = @Content(schema = @Schema(implementation = RecipeDTO.class))
    )
    @GetMapping
    public ResponseEntity<PageResponseDTO<RecipeDTO>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection){
        log.info("GET /api/v1/recipes - Get all recipes");

        PageResponseDTO<RecipeDTO> recipeDTOS = recipeService.findAllPaginated(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(recipeDTOS);
    }

    @Operation(
            summary = "Find recipes with advanced filters",
            description = "Search recipes by name, difficulty, season, categories, labels, scores and time preparation"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipes found",
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class))
            )
    })
    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<RecipeDTO>> searchRecipes(
            @RequestBody RecipeFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection){
        log.info("POST /api/v1/recipes/search/paginated - Find with page:{}, size:{}, sortBy:{}, sortDirection:{}", page, size, sortBy, sortDirection);
        PageResponseDTO<RecipeDTO> recipeDTOS = recipeService.findByFiltersPaginated(filterDTO, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(recipeDTOS);
    }

    @Operation(
            summary = "Create new recipe",
            description = "Create new recipe"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created recipe",
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No valid data"
            )
    })
    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeCreateDTO createDTO){
        log.info("POST /api/v1/recipes - Creating new recipe: {}", createDTO.getName());
        RecipeDTO createdRecipe = recipeService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @Operation(
            summary = "Update recipe",
            description = "Update a recipe that exist identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated recipe",
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found recipe"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data no valid"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeUpdateDTO updateDTO){
        log.info("PUT /api/v1/recipes/{} - Updating recipe", id);

        RecipeDTO updatedRecipe = recipeService.update(id, updateDTO);

        return ResponseEntity.ok(updatedRecipe);
    }

    @Operation(
            summary = "Delete recipe",
            description = "Removed recipe identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Recipe successfully removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipe not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        log.info("DELETE /api/v1/recipes/{} - Deleting recipe", id);
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
