package com.cocina.robocook.repository;

import com.cocina.robocook.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByOrderByNameAsc();

    List<Label> findByNameContaining(String query);
}
