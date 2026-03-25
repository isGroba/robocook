package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class RecipeIngredientUpdateDTO {

    @Positive(message = "The amount must be greater than 0")
    @Schema(description = "Amount")
    private Double amount;

    @Schema(description = "Amount unit")
    private String unit;

    @Schema(description = "It is optional")
    private Boolean optional;
}
