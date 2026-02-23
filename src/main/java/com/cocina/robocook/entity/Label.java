package com.cocina.robocook.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "labels")
    private Set<Recipe> recipes;

    // constructor
    public Label() {}

    public Label(String name) {
        this.name = name;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    //add convenience methods
    public void addRecipe(Recipe tempRecipe){
        if(recipes == null){
            recipes = new HashSet<>();
        }
        recipes.add(tempRecipe);
        tempRecipe.addLabel(this);
    }

    @Override
    public String toString() {
        return "LabelEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
