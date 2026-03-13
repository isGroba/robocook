package com.cocina.robocook.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "calories")
    private int calories;

    @Column(name = "alternative")
    private String alternative;

    // constructor
    public Ingredient(String name, int calories, String alternative) {
        this.name = name;
        this.calories = calories;
        this.alternative = alternative;
    }
}
