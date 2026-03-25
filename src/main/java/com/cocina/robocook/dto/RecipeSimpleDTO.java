package com.cocina.robocook.dto;

import com.cocina.robocook.entity.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Recipe summary")
public class RecipeSimpleDTO {

    @Schema(description = "Identifier", example = "1")
    private Long id;

    @Schema(description = "Recipe name", example = "1")
    private String name;

    @Schema(description = "Description", example = "Traditional omelette... ")
    private String description;

    @Schema(description = "Difficulty", example = "Very hard")
    private Difficulty difficulty;
}
