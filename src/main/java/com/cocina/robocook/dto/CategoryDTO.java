package com.cocina.robocook.dto;

import com.cocina.robocook.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Category")
public class CategoryDTO {

    @JsonProperty("id")
    @Schema(description = "Category ID", example = "1")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Category name", example = "Lunch")
    private String name;

    @JsonProperty("recipes")
    @Schema(description = "List of recipes with this category")
    private Set<Recipe> recipes;


}
