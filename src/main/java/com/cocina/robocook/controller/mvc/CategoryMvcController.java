package com.cocina.robocook.controller.mvc;

import com.cocina.robocook.dto.CategoryCreateDTO;
import com.cocina.robocook.dto.CategoryDTO;
import com.cocina.robocook.dto.CategoryUpdateDTO;
import com.cocina.robocook.dto.IngredientUpdateDTO;
import com.cocina.robocook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryMvcController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public String listCategories(Model model){
        log.info("GET /categories/list - Listing categories in backoffice");

        List<CategoryDTO> theCategories = categoryService.findAll();
        model.addAttribute("categories", theCategories);
        return "category/list-category";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        log.info("GET /categories/showFormForAdd - show creation form");

        model.addAttribute("theCategory", new CategoryCreateDTO());
        return "category/category-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("categoryId") int theId, Model model){
        log.info("GET /categories/showFormForUpdate - show update form");

        CategoryDTO theCategory = categoryService.findById((long) theId);
        model.addAttribute("theCategory", theCategory);

        return "category/category-update-form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("theCategory") CategoryCreateDTO createDTO){
        log.info("POST /categories/save - Save category: {}", createDTO.getName());

        categoryService.create(createDTO);
        return "redirect:/categories/list";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("theCategory") CategoryUpdateDTO updateDTO
            , @ModelAttribute("id") int theId){
        log.info("POST /categories/update - Update category: {}", updateDTO.getName());

        categoryService.update((long)theId, updateDTO);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("categoryId") int theId){
        log.info("GET /categories/delete - Delete category: {}", theId);

        categoryService.deleteById((long)theId);
        return "redirect:/categories/list";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<CategoryDTO> search(@RequestParam String query) {
        log.info("GET /categories/search?query={} - Finding categories", query);

        return categoryService.findByNameContaining(query);
    }
}
