package com.cocina.robocook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "recipes")
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "categories")
    private Set<Recipe> recipes;

    // constructor
    public Category(String name) {
        this.name = name;
    }

    // add convenience methods
    public void addRecipe(Recipe tempRecipe){
        if(recipes == null){
            recipes = new HashSet<>();
        }
        recipes.add(tempRecipe);
        tempRecipe.addCategory(this);
    }

}
