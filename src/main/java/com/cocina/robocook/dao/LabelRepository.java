package com.cocina.robocook.dao;

import com.cocina.robocook.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByOrderByNameAsc();
}
