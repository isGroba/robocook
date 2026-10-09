package com.cocina.robocook.controller.api;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Categories", description = "This section manage the categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get all categories",
            description = "Obtain all categories order by name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of categories successfully obtained",
            content = @Content(schema = @Schema(implementation = CategoryDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCatogories(){
        log.info("GET /api/v1/categories - Get all categories");

        List<CategoryDTO> categoryDTOS = categoryService.findAll();

        return ResponseEntity.ok(categoryDTOS);
    }

    @Operation(
            summary = "Get category by ID",
            description = "Returns a specific category identified by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        log.info("GET /api/v1/categories/{} - Finding category", id);

        CategoryDTO category = categoryService.findById(id);

        return ResponseEntity.ok(category);
    }

    @Operation(
            summary = "Find categories by name",
            description = "Look for categories that contain the specified text"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories found",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam(value = "query", defaultValue = "") String query){
        log.info("GET /api/v1/categories/search?query={} - Finding category", query);

        List<CategoryDTO> categoryDTOS = categoryService.findByNameContaining(query);

        return ResponseEntity.ok(categoryDTOS);
    }

    @Operation(
            summary = "Create new category",
            description = "Create new category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created category",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No valid data"
            )
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryCreateDTO createDTO){
        log.info("POST /api/v1/categories - Creating new category: {}", createDTO.getName());

        CategoryDTO createdCategory = categoryService.create(createDTO);

        return ResponseEntity.ok(createdCategory);
    }

    @Operation(
            summary = "Update category",
            description = "Update a category that exist identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated category",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found category"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data no valid"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO updateDTO){
        log.info("PUT /api/v1/categories/{} - Updating category", id);

        CategoryDTO updatedCategory = categoryService.update(id, updateDTO);

        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(
            summary = "Delete category",
            description = "Removed category identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Category successfully removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        log.info("DELETE /api/v1/categories/{} - Category removed", id);

        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
