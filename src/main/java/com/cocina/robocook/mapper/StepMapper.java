package com.cocina.robocook.mapper;

import com.cocina.robocook.dto.*;
import com.cocina.robocook.entity.Step;
import org.springframework.stereotype.Component;

@Component
public class StepMapper {

    public StepDTO toDTO(Step step){
        if(null == step)
            return null;

        return StepDTO.builder()
                .id(step.getId())
                .orderNumber(step.getOrderNumber())
                .description(step.getDescription())
                .build();
    }

    public Step toEntity(StepCreateDTO createDTO){
        if (null == createDTO)
            return null;

        Step step = new Step();
        step.setOrderNumber(createDTO.getOrderNumber());
        step.setDescription(createDTO.getDescription());

        return step;
    }

    public void updateEntity(StepUpdateDTO updateDTO, Step step){
        if(null ==updateDTO)
            return;

        if(updateDTO.getOrderNumber() != null)
            step.setOrderNumber(updateDTO.getOrderNumber());
        if(null != updateDTO.getDescription())
            step.setDescription(updateDTO.getDescription());

    }
}
