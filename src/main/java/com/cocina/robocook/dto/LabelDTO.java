package com.cocina.robocook.dto;

import com.cocina.robocook.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Label")
public class LabelDTO {

    @JsonProperty("id")
    @Schema(description = "Label ID", example = "1")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Label name", example = "for kids")
    private String name;

    @JsonProperty("recipes")
    @Schema(description = "List of recipes with this label")
    private Set<Recipe> recipes;
}
