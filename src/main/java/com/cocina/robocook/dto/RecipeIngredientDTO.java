package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Recipe ingredient")
public class RecipeIngredientDTO {

    @Schema(description = "Recipe ingredient ID")
    private Long id;

    @Schema(description = "The ingredient")
    private IngredientDTO ingredient;

    @Schema(description = "Amount")
    private Double amount;

    @Schema(description = "Amount unit")
    private String unit;

    @Schema(description = "It is optional")
    private boolean optional;
}
