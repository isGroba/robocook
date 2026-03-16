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
@Table(name = "label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "labels")
    private Set<Recipe> recipes;

    // constructor
    public Label(String name) {
        this.name = name;
    }

    //add convenience methods
    public void addRecipe(Recipe tempRecipe){
        if(recipes == null){
            recipes = new HashSet<>();
        }
        recipes.add(tempRecipe);
        tempRecipe.addLabel(this);
    }

}
