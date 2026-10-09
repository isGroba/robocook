package com.cocina.robocook.repository;

import com.cocina.robocook.entity.Difficulty;
import com.cocina.robocook.entity.Recipe;
import com.cocina.robocook.entity.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findAllByOrderByNameAsc();

    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN r.categories c " +
            "LEFT JOIN r.labels l " +
            "WHERE (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:difficulty IS NULL OR r.difficulty = :difficulty) " +
            "AND (:season IS NULL OR r.season = :season) " +
            "AND (COALESCE(:categoryIds, NULL) IS NULL OR c.id IN :categoryIds) " +
            "AND (COALESCE(:labelIds, NULL) IS NULL OR l.id IN :labelIds) " +
            "AND (:minHealthyScore IS NULL OR r.healthyScore >= :minHealthyScore) " +
            "AND (:maxHealthyScore IS NULL OR r.healthyScore <= :maxHealthyScore) " +
            "AND (:minTasteScore IS NULL OR r.tasteScore >= :minTasteScore) " +
            "AND (:maxTasteScore IS NULL OR r.tasteScore <= :maxTasteScore) " +
            "AND (:preparationTime IS NULL OR r.preparationTime <= :preparationTime) ")
    Page<Recipe> findByFiltersPagination(
            @Param("name") String name,
            @Param("difficulty") Difficulty difficulty,
            @Param("season") Season season,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("labelIds") List<Long> labelIds,
            @Param("minHealthyScore") Integer minHealthyScore,
            @Param("maxHealthyScore") Integer maxHealthyScore,
            @Param("minTasteScore") Integer minTasteScore,
            @Param("maxTasteScore") Integer maxTasteScore,
            @Param("preparationTime") Integer preparationTime,
            Pageable pageable
    );

}
