package com.cocina.robocook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"categories", "labels", "recipeIngredients", "steps"})
@NoArgsConstructor
@Entity
@Table(name="recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

    @Column(name = "preparation_time")
    private String preparationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Column(name = "healthy_score")
    private String healthyScore;

    @Column(name = "taste_score")
    private String tasteScore;

    @Column(name = "porcions")
    private String portions;

    @Column(name = "calories")
    private String calories;

    @Column(name = "save_date", insertable = false, updatable = false)
    private Date saveDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recipe_label",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    @OrderBy("orderNumber ASC")
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    //constructor
    public Recipe(String name, String description, String preparationTime, Difficulty difficulty, Season season, String healthyScore, String tasteScore, String portions, String calories) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.season = season;
        this.healthyScore = healthyScore;
        this.tasteScore = tasteScore;
        this.portions = portions;
        this.calories = calories;
    }

    // add a convenience method
    public void addStep(Step theStep){
        if(steps == null)
            steps = new ArrayList<>();

        steps.add(theStep);
    }

    public void addCategory(Category theCategory){
        if(categories == null)
            categories = new ArrayList<>();

        categories.add(theCategory);
    }

    public void addLabel(Label theLabel){
        if(labels == null)
            labels = new ArrayList<>();

        labels.add(theLabel);
    }

    public void addRecipeIngredient(RecipeIngredient theRecipeIngredient){
        if(recipeIngredients == null)
            recipeIngredients = new ArrayList<>();

        recipeIngredients.add(theRecipeIngredient);
        theRecipeIngredient.setRecipe(this);
    }
}
