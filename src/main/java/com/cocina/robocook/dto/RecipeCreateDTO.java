package com.cocina.robocook.dto;

import com.cocina.robocook.entity.Difficulty;
import com.cocina.robocook.entity.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Recipe")
public class RecipeCreateDTO {

    @NotBlank(message = "The name is mandatory")
    @Size(min = 2, max = 150, message = "The name must be between 2 and 150")
    @Schema(description = "Recipe name", example = "Eggs with rice")
    private String name;

    @Schema(description = "Short text about the recipe")
    private String description;

    @Schema(description = "The recipe preparation time in minutes", example = "15")
    private String preparationTime;

    @Schema(description = "Difficulty in making the recipe")
    private Difficulty difficulty;

    @Schema(description = "Ideal time of year to make the recipe", example = "SUMMER")
    private Season season;

    @Schema(description = "Healthy score from 0 to 100", example = "75")
    private String healthyScore;

    @Schema(description = "Taste score from 0 to 100", example = "80")
    private String tasteScore;

    @Schema(description = "How many people does this recipe serve?", example = "4")
    private String portions;

    @Schema(description = "The calories in this recipe", example = "350")
    private String calories;

}
