package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create step")
public class StepCreateDTO {

    @Min(value = 1, message = "The number must be greater than 0")
    @Schema(description = "Order step execution", example = "1")
    private int orderNumber;

    @NotBlank(message = "Describe the actions in this step")
    @Schema(description = "description", example = "Cut the vegetables....")
    private String description;
}
