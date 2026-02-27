package com.cocina.robocook.controller;

import com.cocina.robocook.dao.RecipeRepository;
import com.cocina.robocook.entity.Label;
import com.cocina.robocook.entity.Recipe;
import com.cocina.robocook.entity.Step;
import com.cocina.robocook.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService service) {
        this.recipeService = service;
    }

    @GetMapping("/list")
    public String listRecipes(Model model){
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);

        return "recipe/list-recipe.html";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Recipe recipe = new Recipe();
        recipe.addStep(new Step());
        model.addAttribute("theRecipe", recipe);

        return "recipe/recipe-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("recipeId") int theId, Model model){
        Recipe theRecipe = recipeService.findById((long)theId);
        model.addAttribute("theRecipe", theRecipe);
        return "recipe/recipe-form";
    }

    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute("theRecipe") Recipe theRecipe){

        recipeService.save(theRecipe);
        return "redirect:/recipes/list";
    }

    @GetMapping("/delete")
    public String deleteLabel(@RequestParam("recipeId") int theId, Model model){
        recipeService.deleteById((long)theId);
        return "redirect:/labels/list";
    }
}
