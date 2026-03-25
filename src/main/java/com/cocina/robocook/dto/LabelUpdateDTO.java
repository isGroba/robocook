package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Update label")
public class LabelUpdateDTO {

    @Size(min = 2, max = 100, message = "The name must be have between 2 and 100 characters long")
    @Schema(description = "Label name", example = "For kids")
    private String name;

}
