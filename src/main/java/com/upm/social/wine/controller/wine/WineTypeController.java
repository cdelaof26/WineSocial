package com.upm.social.wine.controller.wine;

import com.upm.social.wine.assembler.wine.WineTypeModelAssembler;
import com.upm.social.wine.entity.GeneralUtilities;
import java.util.List;
import com.upm.social.wine.entity.wine.WineType;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.service.wine.WineTypeService;
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
 * WineType controller
 * @author cristopher
 */
@RestController
@RequestMapping("/winetypes")
public class WineTypeController {
    private final WineTypeService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(WineTypeController.class);
    
    private final PagedResourcesAssembler<WineType> pagedResourcesAssembler;
    private final WineTypeModelAssembler wineTypeModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(WineTypeController.class);

    public WineTypeController(WineTypeService service, PagedResourcesAssembler<WineType> pagedResourcesAssembler, WineTypeModelAssembler wineTypeModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.wineTypeModelAssembler = wineTypeModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<WineType>> fetchWineTypes(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch wineTypes: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<WineType> wineTypes = service.fetchWineTypes(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(wineTypes, wineTypeModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<WineType> getWineType(@PathVariable Integer id) {
        logger.debug("Fetch wineType %d", id);
        
        Optional<WineType> _wineType = service.searchWineTypeById(id);
        if (_wineType.isEmpty())
            throw new ObjectNotFoundException("El tipo de vino", id);
        
        WineType wineType = _wineType.get();
        logger.debug("WineType found %s", wineType);
        wineType.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WineTypeController.class).getWineType(id)).withSelfRel());
        
        return ResponseEntity.ok(wineType);
    }
    
    private void validateFields(WineType wineType) {
        if (wineType.getName().length() > 128)
            throw new FieldTooLongException(wineType.getName().length());
        
        if (wineType.getDescription() != null && wineType.getDescription().length() > 1024)
            throw new FieldTooLongException("La dirección postal", wineType.getDescription().length(), 1024);
    }
    
    @PostMapping()
    public ResponseEntity<List<WineType>> createWineType(@Valid @RequestBody WineType newWineType) {
        logger.debug("Creating wineType %s...", newWineType);
        
        if (service.existsByName(newWineType.getName()))
            throw new ObjectExistsException(newWineType.getName());
        
        validateFields(newWineType);
        
        WineType wineType = service.saveWineType(newWineType);
        return ResponseEntity.created(self.slash(wineType.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<WineType> updateWineType(@Valid @RequestBody WineType newWineType, @PathVariable Integer id) {
        logger.debug("Update wineType %d", id);
        
        Optional<WineType> _wineType = service.searchWineTypeById(id);
        if (_wineType.isEmpty())
            throw new ObjectNotFoundException("El tipo de vino", id);
        
        WineType wineType = _wineType.get();
        logger.debug("WineType found %s", wineType);
        logger.debug("WineType update %s", newWineType);
        
        GeneralUtilities.copyAToB(newWineType, wineType);
        validateFields(wineType);
        service.saveWineType(wineType);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWineType(@PathVariable Integer id) {
        logger.debug("Deleting wineType %d", id);
        
        if (service.existsById(id)) {
            service.dropWineType(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("El tipo de vino", id);
    }
}
