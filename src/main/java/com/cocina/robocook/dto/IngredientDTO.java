package com.cocina.robocook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Ingredient")
public class IngredientDTO {

    @JsonProperty("id")
    @Schema(description = "Ingredient ID", example = "1")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Ingredient name", example = "Tomato")
    private String name;

    @JsonProperty("calories")
    @Schema(description = "Calories of one ingredient", example = "100")
    private int calories;

    @JsonProperty("alternative")
    @Schema(description = "Alternative ingredient", example = "Tomato sauce")
    private String alternative;
}
