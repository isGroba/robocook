package com.cocina.robocook.service;

import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;

import java.util.List;

public interface LabelService {

    List<LabelDTO> findAll();

    LabelDTO findById(Long id);

    List<LabelDTO> findByNameContaining(String query);


    LabelDTO create(LabelCreateDTO createDTO);

    LabelDTO update(Long id, LabelUpdateDTO updateDTO);

    void deleteById(Long id);

}
