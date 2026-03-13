package com.cocina.robocook.controller.api;

import com.cocina.robocook.dto.IngredientCreateDTO;
import com.cocina.robocook.dto.IngredientDTO;
import com.cocina.robocook.dto.IngredientUpdateDTO;
import com.cocina.robocook.service.IngredientService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Ingredients", description = "This section manage ingredients")
public class IngredientApiController {

    private final IngredientService ingredientService;

    @Operation(
            summary = "Get all ingredients",
            description = "Obtain all ingredients order by name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of ingredients successfully obtained",
            content = @Content(schema = @Schema(implementation = IngredientDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients(){
        log.info("GET /api/v1/ingredients - Get all ingredients");

        List<IngredientDTO> ingredientDTOS = ingredientService.findAll();

        return ResponseEntity.ok(ingredientDTOS);
    }

    @Operation(
            summary = "Get ingredient by ID",
            description = "Returns a specific ingredient identified by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ingredient found",
                    content = @Content(schema = @Schema(implementation = IngredientDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ingredient not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        log.info("GET /api/v1/ingredients/{} - Finding ingredient", id);

        IngredientDTO ingredient = ingredientService.findById(id);

        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Find ingredients by name",
            description = "Look for ingredients that contain the specified text"
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Ingredients found",
                content = @Content(schema = @Schema(implementation = IngredientDTO.class))
        )
    })
    @GetMapping("/search")
    public ResponseEntity<List<IngredientDTO>> searchIngredients(@RequestParam(value = "query", defaultValue = "") String query){
        log.info("GET /api/v1/ingredients/search?query={} - Finding ingredient", query);

        List<IngredientDTO> ingredientDTOS = ingredientService.findByNameContaining(query);

        return ResponseEntity.ok(ingredientDTOS);
    }

    @Operation(
            summary = "Create new ingredient",
            description = "Create new ingredient"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created ingredient",
                    content = @Content(schema = @Schema(implementation = IngredientDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No valid data"
            )
    })
    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient (@Valid @RequestBody IngredientCreateDTO createDTO){
        log.info("POST /api/v1/ingredients - Creating new ingredient: {}", createDTO.getName());

        IngredientDTO createdIngredient = ingredientService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
    }

    @Operation(
            summary = "Update ingredient",
            description = "Update a ingredient that exist identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated ingredient",
                    content = @Content(schema = @Schema(implementation = IngredientDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found ingredient"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data no valid"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(
            @PathVariable Long id,
            @Valid @RequestBody IngredientUpdateDTO updateDTO) {

        log.info("PUT /api/v1/ingredients/{} - Updating ingredient", id);

        IngredientDTO updatedIngredient = ingredientService.update(id, updateDTO);

        return ResponseEntity.ok(updatedIngredient);
    }

    @Operation(
            summary = "Eliminar ingrediente",
            description = "Elimina un ingrediente identificado por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Ingrediente eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ingrediente no encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        log.info("DELETE /api/v1/ingredients/{} - Eliminando ingrediente", id);

        ingredientService.deleteById(id);

        // Devolver 204 (No Content) sin cuerpo
        return ResponseEntity.noContent().build();
    }

}
