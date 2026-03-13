package com.cocina.robocook.controller.mvc;

import com.cocina.robocook.dto.IngredientCreateDTO;
import com.cocina.robocook.dto.IngredientDTO;
import com.cocina.robocook.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientMvcController {
    private final IngredientService ingredientService;

    @GetMapping("/list")
    public String listIngredients(Model model){
        log.info("GET /ingredients/list - Listing ingredients in backoffice");

        List<IngredientDTO> ingredients = ingredientService.findAll();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("title", "Ingredient");

        return "ingredient/list-ingredient";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        log.info("GET /ingredients/showFormForAdd - show creation form");

        IngredientDTO ingredientDTO = new IngredientDTO();
        model.addAttribute("theIngredient", ingredientDTO);
        model.addAttribute("title", "Ingredient");

        return "ingredient/ingredient-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("ingredientId") int theId, Model model){
        log.info("GET /ingredients/showFormForUpdate - show update form");

        IngredientDTO theIngredient = ingredientService.findById((long) theId);
        model.addAttribute("theIngredient", theIngredient);
        model.addAttribute("title", "Ingredient");

        return "ingredient/ingredient-form";
    }

    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute("theIngredient") IngredientCreateDTO createDTO){
        log.info("POST /ingredients/save - Save ingredient: {}", createDTO.getName());

        ingredientService.create(createDTO);
        return "redirect:/ingredients/list";
    }

    @GetMapping("/delete")
    public String deleteIngredient(@RequestParam("ingredientId") int theId){
        log.info("GET /ingredients/delete - Delete ingredient: {}", theId);

        ingredientService.deleteById((long)theId);
        return "redirect:/ingredients/list";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<IngredientDTO> search(@RequestParam String query) {
        log.info("GET /ingredients/search?query={} - Finding ingredients", query);

        return ingredientService.findByNameContaining(query);
    }
}
