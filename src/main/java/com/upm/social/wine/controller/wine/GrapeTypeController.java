package com.upm.social.wine.controller.wine;

import com.upm.social.wine.assembler.wine.GrapeTypeModelAssembler;
import com.upm.social.wine.entity.GeneralUtilities;
import java.util.List;
import com.upm.social.wine.entity.wine.GrapeType;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.service.wine.GrapeTypeService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * GrapeType controller
 * @author cristopher
 */
@RestController
@RequestMapping("/grapetypes")
public class GrapeTypeController {
    private final GrapeTypeService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(GrapeTypeController.class);
    
    private final PagedResourcesAssembler<GrapeType> pagedResourcesAssembler;
    private final GrapeTypeModelAssembler grapeTypeModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(GrapeTypeController.class);

    public GrapeTypeController(GrapeTypeService service, PagedResourcesAssembler<GrapeType> pagedResourcesAssembler, GrapeTypeModelAssembler grapeTypeModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.grapeTypeModelAssembler = grapeTypeModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<GrapeType>> fetchGrapeTypes(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch grapeTypes: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<GrapeType> grapeTypes = service.fetchGrapeTypes(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(grapeTypes, grapeTypeModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<GrapeType> getGrapeType(@PathVariable Integer id) {
        logger.debug("Fetch grapeType %d", id);
        
        Optional<GrapeType> _grapeType = service.searchGrapeTypeById(id);
        if (_grapeType.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        GrapeType grapeType = _grapeType.get();
        logger.debug("GrapeType found %s", grapeType);
        grapeType.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrapeTypeController.class).getGrapeType(id)).withSelfRel());
        
        return ResponseEntity.ok(grapeType);
    }
    
    private void validateFields(GrapeType grapeType) {
        if (grapeType.getName().length() > 128)
            throw new FieldTooLongException(grapeType.getName().length());
    }
    
    @PostMapping()
    public ResponseEntity<List<GrapeType>> createGrapeType(@Valid @RequestBody GrapeType newGrapeType) {
        logger.debug("Creating grapeType %s...", newGrapeType);
        
        if (service.existsByName(newGrapeType.getName()))
            throw new ObjectExistsException(newGrapeType.getName());
        
        validateFields(newGrapeType);
        
        GrapeType grapeType = service.saveGrapeType(newGrapeType);
        return ResponseEntity.created(self.slash(grapeType.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<GrapeType> updateGrapeType(@Valid @RequestBody GrapeType newGrapeType, @PathVariable Integer id) {
        logger.debug("Update grapeType %d", id);
        
        Optional<GrapeType> _grapeType = service.searchGrapeTypeById(id);
        if (_grapeType.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        GrapeType grapeType = _grapeType.get();
        logger.debug("GrapeType found %s", grapeType);
        logger.debug("GrapeType update %s", newGrapeType);
        
        GeneralUtilities.copyAToB(newGrapeType, grapeType);
        validateFields(grapeType);
        service.saveGrapeType(grapeType);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGrapeType(@PathVariable Integer id) {
        logger.debug("Deleting grapeType %d", id);
        
        if (service.existsById(id)) {
            service.dropGrapeType(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("La bodega", id);
    }
}
