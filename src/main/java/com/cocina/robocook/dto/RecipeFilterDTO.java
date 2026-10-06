package com.cocina.robocook.dto;

import com.cocina.robocook.entity.Difficulty;
import com.cocina.robocook.entity.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Advanced search filters for recipes")
public class RecipeFilterDTO {

    @Schema(description = "Recipe name", example = "Eggs with rice")
    private String name;

    @Schema(description = "The recipe preparation time in minutes", example = "15")
    private Integer preparationTime;

    @Schema(description = "Difficulty in making the recipe")
    private Difficulty difficulty;

    @Schema(description = "Ideal time of year to make the recipe", example = "SUMMER")
    private Season season;

    @Schema(description = "Minimum healthy score", example = "50")
    private Integer minHealthyScore;

    @Schema(description = "Maximum healthy score", example = "100")
    private Integer maxHealthyScore;

    @Schema(description = "Minimum taste score", example = "60")
    private Integer minTasteScore;

    @Schema(description = "Maximum taste score", example = "100")
    private Integer maxTasteScore;

    @Schema(description = "Categories assigned to this recipe", example = "1")
    private List<Long> categoryIds = new ArrayList<>();

    @Schema(description = "Labels assigned to this recipe", example = "1")
    private List<Long> labelIds = new ArrayList<>();

    @Schema(description = "Sort by field (name, difficulty, season, healthyScore, tasteScore and preparationTime)", example = "name")
    private String sortBy;

    @Schema(description = "Sort direction (asc, desc)", example = "asc")
    private String sortDirection;
}
