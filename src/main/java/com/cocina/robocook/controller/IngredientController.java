package com.cocina.robocook.controller;

import com.cocina.robocook.entity.Ingredient;
import com.cocina.robocook.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/list")
    public String listIngredients(Model model){
        List<Ingredient> theIngredient = ingredientService.findAll();
        model.addAttribute("ingredients", theIngredient);
        model.addAttribute("title", "Ingredient");
        return "ingredient/list-ingredient";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Ingredient ingredient = new Ingredient();
        model.addAttribute("theIngredient", ingredient);
        model.addAttribute("title", "Ingredient");
        return "ingredient/ingredient-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("ingredientId") int theId, Model model){

        Ingredient theIngredient = ingredientService.findById((long) theId);
        model.addAttribute("theIngredient", theIngredient);
        model.addAttribute("title", "Ingredient");
        return "ingredient/ingredient-form";
    }

    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute("theIngredient") Ingredient theIngredient){

        ingredientService.save(theIngredient);
        return "redirect:/ingredients/list";
    }

    @GetMapping("/delete")
    public String deleteIngredient(@RequestParam("ingredientId") int theId, Model model){
        ingredientService.deleteById((long)theId);
        return "redirect:/ingredients/list";
    }

}
