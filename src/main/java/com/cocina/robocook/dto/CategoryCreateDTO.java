package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Create category")
public class CategoryCreateDTO {

    @NotBlank(message = "The name is mandatory")
    @Size(min = 2, max = 100, message = "The name must be have between 2 and 100 characters long")
    @Schema(description = "Category name", example = "Dinner")
    private String name;

}
