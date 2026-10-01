package com.cocina.robocook.controller.api;

import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;
import com.cocina.robocook.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/labels")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Labels", description = "This section manage the labels")
public class LabelApiController {

    private final LabelService labelService;

    @Operation(
            summary = "Get all labels",
            description = "Obtain all labels order by name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of labels successfully obtained",
            content = @Content(schema = @Schema(implementation = LabelDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<LabelDTO>> getAllLabels(){
        log.info("GET /api/v1/labels - Get all labels");

        List<LabelDTO> labelDTOS = labelService.findAll();

        return ResponseEntity.ok(labelDTOS);
    }

    @Operation(
            summary = "Get label by ID",
            description = "Returns a specific label identified by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Label found",
                    content = @Content(schema = @Schema(implementation = LabelDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Label not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> getLabelById(@PathVariable Long id){
        log.info("GET /api/v1/labels/{} - Finding label", id);

        LabelDTO label = labelService.findById(id);

        return ResponseEntity.ok(label);
    }

    @Operation(
            summary = "Find labels by name",
            description = "Look for labels that contain the specified text"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Labels found",
                    content = @Content(schema = @Schema(implementation = LabelDTO.class))
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<LabelDTO>> searchLabels(@RequestParam(value = "query", defaultValue = "") String query){
        log.info("GET /api/v1/labels/search?query={} - Finding label", query);

        List<LabelDTO> labelDTOS = labelService.findByNameContaining(query);

        return ResponseEntity.ok(labelDTOS);
    }

    @Operation(
            summary = "Create new label",
            description = "Create new label"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created label",
                    content = @Content(schema = @Schema(implementation = LabelDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No valid data"
            )
    })
    @PostMapping
    public ResponseEntity<LabelDTO> createLabel(@Valid @RequestBody LabelCreateDTO createDTO){
        log.info("POST /api/v1/labels - Creating new label: {}", createDTO.getName());

        LabelDTO createdLabel = labelService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLabel);
    }

    @Operation(
            summary = "Update label",
            description = "Update a label that exist identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated label",
                    content = @Content(schema = @Schema(implementation = LabelDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found label"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data no valid"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<LabelDTO> updateLabel(@PathVariable Long id, @Valid @RequestBody LabelUpdateDTO updateDTO){
        log.info("PUT /api/v1/labels/{} - Updating label", id);

        LabelDTO updatedLabel = labelService.update(id, updateDTO);

        return ResponseEntity.ok(updatedLabel);
    }

    @Operation(
            summary = "Delete label",
            description = "Removed label identified by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Label successfully removed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Label not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id){
        log.info("DELETE /api/v1/labels/{} - Deleting label", id);

        labelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
