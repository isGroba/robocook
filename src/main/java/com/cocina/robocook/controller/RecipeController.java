package com.cocina.robocook.controller;

import com.cocina.robocook.entity.*;
import com.cocina.robocook.service.CategoryService;
import com.cocina.robocook.service.LabelService;
import com.cocina.robocook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private RecipeService recipeService;
    private LabelService labelService;
    private CategoryService categoryService;

    public RecipeController(RecipeService service, LabelService labelService, CategoryService categoryService) {
        this.recipeService = service;
        this.labelService = labelService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listRecipes(Model model){
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);

        return "recipe/list-recipe.html";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("seasons", Season.values());

        Recipe recipe = new Recipe();
        recipe.addStep(new Step());

        model.addAttribute("theRecipe", recipe);

        return "recipe/recipe-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("recipeId") int theId, Model model){
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("seasons", Season.values());

        Recipe theRecipe = recipeService.findById((long)theId);
        model.addAttribute("theRecipe", theRecipe);
        return "recipe/recipe-form";
    }

    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute("theRecipe") Recipe formRecipe){

        Recipe recipeDB;
        List<Label> labels = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        Date today = new Date();

        if(formRecipe.getId() != null){
            recipeDB = recipeService.findById(formRecipe.getId());
        }else{
            recipeDB = new Recipe();
        }

        recipeDB.setId(formRecipe.getId());
        recipeDB.setName(formRecipe.getName());
        recipeDB.setDescription(formRecipe.getDescription());
        recipeDB.setPreparationTime(formRecipe.getPreparationTime());
        recipeDB.setDifficulty(formRecipe.getDifficulty());
        recipeDB.setSeason(formRecipe.getSeason());
        recipeDB.setHealthyScore(formRecipe.getHealthyScore());
        recipeDB.setTasteScore(formRecipe.getTasteScore());
        recipeDB.setPortions(formRecipe.getPortions());
        recipeDB.setCalories(formRecipe.getCalories());

        if(null != recipeDB.getSteps())
            recipeDB.getSteps().clear();
        if(null != formRecipe.getSteps())
            for(Step s: formRecipe.getSteps()){
                recipeDB.addStep(s);
            }

        if(null != recipeDB.getLabels())
            recipeDB.getLabels().clear();
        if(null != formRecipe.getLabels())
            for(Label l: formRecipe.getLabels()){
                Label label = labelService.findById(l.getId());
                recipeDB.addLabel(label);
            }

        if(null != recipeDB.getCategories())
            recipeDB.getCategories().clear();
        if(null != formRecipe.getCategories())
            for(Category c: formRecipe.getCategories()){
                Category category = categoryService.findById(c.getId());
                recipeDB.addCategory(category);
            }

        if(null != recipeDB.getRecipeIngredients())
            recipeDB.getRecipeIngredients().clear();
        if(null != formRecipe.getRecipeIngredients())
            for(RecipeIngredient ri: formRecipe.getRecipeIngredients()){
                recipeDB.addRecipeIngredient(ri);
            }

        recipeService.save(recipeDB);
        return "redirect:/recipes/list";
    }

    @GetMapping("/delete")
    public String deleteLabel(@RequestParam("recipeId") int theId, Model model){
        recipeService.deleteById((long)theId);
        return "redirect:/recipes/list";
    }
}
