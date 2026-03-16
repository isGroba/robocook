package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;
import com.cocina.robocook.entity.Label;
import org.springframework.stereotype.Component;

@Component
public class LabelMapper {

    public LabelDTO toDTO(Label label){
        if(null == label)
            return null;

        return LabelDTO.builder()
                .id(label.getId())
                .name(label.getName())
                .recipes(label.getRecipes())
                .build();
    }

    public Label toEntity(LabelCreateDTO createDTO){
        if(null == createDTO)
            return null;

        Label label = new Label();
        label.setName(createDTO.getName());
        label.setRecipes(createDTO.getRecipes());

        return label;
    }

    public void updateEntity(LabelUpdateDTO updateDTO, Label label){
        if (updateDTO == null)
            return;

        if (updateDTO.getName() != null)
            label.setName(updateDTO.getName());
        if(updateDTO.getRecipes() != null)
            label.setRecipes(updateDTO.getRecipes());
    }
}
