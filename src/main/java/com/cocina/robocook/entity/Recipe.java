package com.cocina.robocook.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Dificulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @Column(name = "healthy_score")
    private String healthyScore;

    @Column(name = "taste_score")
    private String tasteScore;

    @Column(name = "porcions")
    private String porcions;

    @Column(name = "calories")
    private String calories;

    @Column(name = "save_date")
    private Date saveDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "recipe_label",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<Label> labels;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    @OrderBy("orderNumber ASC")
    private List<Step> steps;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients;

    //constructor
    public Recipe(){}

    public Recipe(String name, String description, String preparationTime, Dificulty difficulty, Season season, String healthyScore, String tasteScore, String porcions, String calories) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.season = season;
        this.healthyScore = healthyScore;
        this.tasteScore = tasteScore;
        this.porcions = porcions;
        this.calories = calories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Dificulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Dificulty difficulty) {
        this.difficulty = difficulty;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getHealthyScore() {
        return healthyScore;
    }

    public void setHealthyScore(String healthyScore) {
        this.healthyScore = healthyScore;
    }

    public String getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(String tasteScore) {
        this.tasteScore = tasteScore;
    }

    public String getPorcions() {
        return porcions;
    }

    public void setPorcions(String porcions) {
        this.porcions = porcions;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    // add a convenience method
    public void addStep(Step theStep){
        if(steps == null)
            steps = new ArrayList<>();

        steps.add(theStep);
    }

    public void addCategory(Category theCategory){
        if(categories == null)
            categories = new HashSet<>();

        categories.add(theCategory);
    }

    public void addLabel(Label theLabel){
        if(labels == null)
            labels = new HashSet<>();

        labels.add(theLabel);
    }

    public void addRecipeIngredient(RecipeIngredient theRecipeIngredient){
        if(recipeIngredients == null)
            recipeIngredients = new HashSet<>();

        recipeIngredients.add(theRecipeIngredient);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime='" + preparationTime + '\'' +
                ", difficulty=" + difficulty +
                ", season=" + season +
                ", healthyScore='" + healthyScore + '\'' +
                ", tasteScore='" + tasteScore + '\'' +
                ", porcions='" + porcions + '\'' +
                ", calories='" + calories + '\'' +
                ", saveDate=" + saveDate +
                '}';
    }
}
