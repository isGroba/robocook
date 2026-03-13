package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO to create ingredient")
public class IngredientCreateDTO {

    @NotBlank(message = "The name is mandatory")
    @Size(min = 2, max = 100, message = "The name must be have between 2 and 100 characters long")
    @Schema(description = "Ingredient name", example = "Tomato")
    private String name;

    @Schema(description = "Calories of one ingredient", example = "100")
    private int calories;

    @Size(max = 100, message = "The alternative ingredient must be have between 2 and 100 characters long")
    @Schema(description = "Alternative ingredient", example = "Tomato sauce")
    private String alternative;
}
