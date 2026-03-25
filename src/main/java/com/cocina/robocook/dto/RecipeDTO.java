package com.cocina.robocook.dto;

import com.cocina.robocook.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class RecipeDTO {

    @Schema(description = "Recipe ID", example = "1")
    private Long id;

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

    @Schema(description = "Now")
    private Date saveDate;

    @Schema(description = "Categories assigned to this recipe", example = "1")
    private List<CategoryDTO> categories = new ArrayList<>();

    @Schema(description = "Labels assigned to this recipe", example = "1")
    private List<LabelDTO> labels = new ArrayList<>();

    @Schema(description = "Steps to take", example = "1")
    private List<StepDTO> steps = new ArrayList<>();

    @Schema(description = "The ingredients", example = "1")
    private List<RecipeIngredientDTO> recipeIngredients = new ArrayList<>();
}
