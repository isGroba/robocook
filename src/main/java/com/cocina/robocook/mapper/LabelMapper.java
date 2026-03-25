package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;
import com.cocina.robocook.entity.Label;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class LabelMapper {

    private final RecipeSimpleMapper recipeSimpleMapper;

    public LabelDTO toDTO(Label label){
        if(null == label)
            return null;

        return LabelDTO.builder()
                .id(label.getId())
                .name(label.getName())
                .recipes(null !=label.getRecipes()?
                        label.getRecipes().stream()
                                .map(recipeSimpleMapper::toDTO)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public Label toEntity(LabelCreateDTO createDTO){
        if(null == createDTO)
            return null;

        Label label = new Label();
        label.setName(createDTO.getName());

        return label;
    }

    public void updateEntity(LabelUpdateDTO updateDTO, Label label){
        if (updateDTO == null)
            return;

        if (updateDTO.getName() != null)
            label.setName(updateDTO.getName());
    }
}
