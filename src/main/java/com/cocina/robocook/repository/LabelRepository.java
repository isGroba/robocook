package com.cocina.robocook.repository;

import com.cocina.robocook.entity.Label;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
  List<Label> findAllByOrderByNameAsc();

  List<Label> findByNameContaining(String query);
}
