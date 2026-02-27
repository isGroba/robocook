package com.cocina.robocook.controller;

import com.cocina.robocook.entity.Category;
import com.cocina.robocook.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listCategories(Model model){
        List<Category> theCategories = categoryService.findAll();
        model.addAttribute("categories", theCategories);
        return "category/list-category";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Category category = new Category();
        model.addAttribute("theCategory", category);
        return "category/category-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("categoryId") int theId, Model model){

        Category theCategory = categoryService.findById((long) theId);
        model.addAttribute("theCategory", theCategory);
        return "category/category-form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("theCategory") Category theCategory){

        categoryService.save(theCategory);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("categoryId") int theId){
        categoryService.deleteById((long)theId);
        return "redirect:/categories/list";
    }


}
