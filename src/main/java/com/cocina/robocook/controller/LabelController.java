package com.cocina.robocook.controller;

import com.cocina.robocook.entity.Label;
import com.cocina.robocook.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/labels")
public class LabelController {
    private LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/list")
    public String listLabels(Model model){
        List<Label> theLabels = labelService.findAll();
        model.addAttribute("labels", theLabels);
        return "label/list-label";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Label label = new Label();
        model.addAttribute("theLabel", label);
        return "label/label-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("labelId") int theId, Model model){

        Label theLabel = labelService.findById((long) theId);
        model.addAttribute("theLabel", theLabel);

        // send over to our form
        return "label/label-form";
    }

    @PostMapping("/save")
    public String saveLabel(@ModelAttribute("theLabel") Label theLabel){

        labelService.save(theLabel);
        return "redirect:/labels/list";
    }

    @GetMapping("/delete")
    public String deleteLabel(@RequestParam("labelId") int theId, Model model){
        labelService.deleteById((long)theId);
        return "redirect:/labels/list";
    }
}
