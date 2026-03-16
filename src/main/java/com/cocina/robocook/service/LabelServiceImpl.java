package com.cocina.robocook.service;

import com.cocina.robocook.dto.LabelCreateDTO;
import com.cocina.robocook.dto.LabelDTO;
import com.cocina.robocook.dto.LabelUpdateDTO;
import com.cocina.robocook.exception.ResourceNotFoundException;
import com.cocina.robocook.mapper.LabelMapper;
import com.cocina.robocook.repository.LabelRepository;
import com.cocina.robocook.entity.Label;
import com.cocina.robocook.entity.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LabelServiceImpl implements LabelService{

    private final LabelRepository repository;
    private final LabelMapper labelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LabelDTO> findAll() {
        log.debug("Get order list by label name");

        return repository.findAllByOrderByNameAsc()
                .stream()
                .map(labelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LabelDTO findById(Long id) {
        log.debug("Finding Label with id: {}", id);

        Label result = repository.findById(id)
                .orElseThrow(()->{
                    log.error("");
                    return new ResourceNotFoundException("");
                });

        return labelMapper.toDTO(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelDTO> findByNameContaining(String query) {
        log.debug("Finding labels that contains {}", query);

        if(null == query || query.isEmpty())
            return List.of();

        return repository.findByNameContaining(query)
                .stream()
                .map(labelMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public LabelDTO create(LabelCreateDTO createDTO) {
        log.debug("Creating new label {}", createDTO.getName());

        Label savedLabel = repository.save(labelMapper.toEntity(createDTO));
        log.info("Created new label with ID: {}", savedLabel.getId());

        return labelMapper.toDTO(savedLabel);
    }

    @Override
    public LabelDTO update(Long id, LabelUpdateDTO updateDTO) {
        log.debug("Updating labels with ID {}", id);

        Label label = repository.findById(id)
                .orElseThrow(()->{
                    log.error("Label Not found with ID: {}", id);
                    return new ResourceNotFoundException("Label Not found with ID: " + id);
                });
        labelMapper.updateEntity(updateDTO, label);

        Label labelUpdated = repository.save(label);

        return labelMapper.toDTO(labelUpdated);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting labels with ID {}", id);

        Label result = repository.findById(id)
                .orElseThrow(()->{
                    log.error("Label not found with ID: {}", id);
                    return new ResourceNotFoundException("Label not found with ID: " + id);
                });

        Set<Recipe> recipes = result.getRecipes();
        for(Recipe recipe: recipes){
            recipe.getLabels().remove(result);
        }
        repository.deleteById(id);
    }

}
