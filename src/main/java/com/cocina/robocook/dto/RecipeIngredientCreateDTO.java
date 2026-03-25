package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Recipe ingredient")
public class RecipeIngredientCreateDTO {

    @NotNull(message = "The id is mandatory")
    @Schema(description = "Recipe ingredient ID")
    private Long id;

    @Schema(description = "The ingredient id")
    private Long ingredientId;

    @Positive(message = "The amount must be greater than 0")
    @Schema(description = "Amount")
    private Double amount;

    @NotBlank(message = "The unity is mandatory")
    @Schema(description = "Amount unit")
    private String unit;

    @Schema(description = "It is optional")
    private boolean optional;
}
