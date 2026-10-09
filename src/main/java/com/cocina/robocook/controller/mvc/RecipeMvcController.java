package com.cocina.robocook.controller.mvc;

import com.cocina.robocook.dto.RecipeDTO;
import com.cocina.robocook.dto.StepDTO;
import com.cocina.robocook.entity.*;
import com.cocina.robocook.service.CategoryService;
import com.cocina.robocook.service.LabelService;
import com.cocina.robocook.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/backoffice/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeMvcController {

    private static final String REDIRECT_LIST = "redirect:/backoffice/recipes/list";
    private final RecipeService recipeService;
    private final LabelService labelService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String listRecipes(Model model){
        log.info("GET /recipes/list - Listing recipes");

        List<RecipeDTO> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);

        return "recipe/list-recipe";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        log.info("GET /recipes/showFormForAdd - show creation form");

        RecipeDTO recipe = new RecipeDTO();
        ArrayList<StepDTO> stepDTOS = new ArrayList<StepDTO>();
        stepDTOS.add(new StepDTO());
        recipe.setSteps(stepDTOS);

        model.addAttribute("theRecipe", recipe);
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("seasons", Season.values());

        return "recipe/recipe-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("recipeId") int theId, Model model){
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("seasons", Season.values());

        RecipeDTO theRecipe = recipeService.findById((long)theId);
        model.addAttribute("theRecipe", theRecipe);
        return "recipe/recipe-update-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("theRecipe") RecipeDTO formRecipe){
        log.info("POST /recipes/save - Saving complete recipe");
        recipeService.createComplete(formRecipe);
        return REDIRECT_LIST;
    }

    @PostMapping("/update")
    public String updateRecipe(@ModelAttribute("theRecipe") RecipeDTO formRecipe){
        log.info("POST /recipes/update - Updating complete recipe");
        recipeService.updateComplete(formRecipe);
        return REDIRECT_LIST;
    }

    @GetMapping("/delete")
    public String deleteLabel(@RequestParam("recipeId") int theId, Model model){
        recipeService.deleteById((long)theId);
        return REDIRECT_LIST;
    }
}
