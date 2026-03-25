package com.cocina.robocook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Recipe steps")
public class StepDTO {

    @JsonProperty("id")
    @Schema(description = "Identifier", example = "1")
    private Long id;

    @JsonProperty("orderNumber")
    @Schema(description = "Order step execution", example = "1")
    private int orderNumber;

    @Schema(description = "description", example = "Cut the vegetables....")
    private String description;

}
