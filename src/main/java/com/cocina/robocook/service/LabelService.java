package com.cocina.robocook.service;

import com.cocina.robocook.entity.Label;

import java.util.List;

public interface LabelService {

    List<Label> findAll();

    Label findById(Long id);

    Label save(Label label);

    void deleteById(Long id);

}
