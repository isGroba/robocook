package com.cocina.robocook.controller.mvc;

import com.cocina.robocook.dto.CategoryUpdateDTO;
import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;
import com.cocina.robocook.service.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/labels")
@RequiredArgsConstructor
@Slf4j
public class LabelMvcController {

    private final LabelService labelService;


    @GetMapping("/list")
    public String listLabels(Model model){
        log.info("GET /labels/list - Listing labels in backoffice");

        List<LabelDTO> theLabels = labelService.findAll();
        model.addAttribute("labels", theLabels);
        return "label/list-label";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        log.info("GET /labels/showFormForAdd - show creation form");

        model.addAttribute("theLabel", new LabelCreateDTO());
        return "label/label-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("labelId") int theId, Model model){
        log.info("GET /labels/showFormForUpdate - show update form");

        LabelDTO theLabel = labelService.findById((long) theId);
        model.addAttribute("theLabel", theLabel);

        // send over to our form
        return "label/label-update-form";
    }

    @PostMapping("/save")
    public String saveLabel(@ModelAttribute("theLabel") LabelCreateDTO createDTO){
        log.info("POST /labels/save - Save label: {}", createDTO.getName());

        labelService.create(createDTO);
        return "redirect:/labels/list";
    }

    @PostMapping("/update")
    public String updateLabel(@ModelAttribute("theLabel") LabelUpdateDTO updateDTO
            , @ModelAttribute("id") int theId){
        log.info("POST /labels/update - Update label: {}", updateDTO.getName());

        labelService.update((long)theId, updateDTO);
        return "redirect:/labels/list";
    }

    @GetMapping("/delete")
    public String deleteLabel(@RequestParam("labelId") int theId, Model model){
        log.info("GET /labels/delete - Delete label: {}", theId);

        labelService.deleteById((long)theId);
        return "redirect:/labels/list";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<LabelDTO> search(@RequestParam String query) {
        log.info("GET /labels/search?query={} - Finding labels", query);

        return labelService.findByNameContaining(query);
    }

}
