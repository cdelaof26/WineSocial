package com.upm.social.wine.controller.wine;

import com.upm.social.wine.assembler.wine.WineModelAssembler;
import com.upm.social.wine.entity.GeneralUtilities;
import com.upm.social.wine.entity.wine.ReducedWine;
import java.util.List;
import com.upm.social.wine.entity.wine.Wine;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.service.wine.WineService;
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
 * Wine controller
 * @author cristopher
 */
@RestController
@RequestMapping("/wines")
public class WineController {
    private final WineService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(WineController.class);
    
    private final PagedResourcesAssembler<ReducedWine> pagedResourcesAssembler;
    private final WineModelAssembler wineModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(WineController.class);

    public WineController(WineService service, PagedResourcesAssembler<ReducedWine> pagedResourcesAssembler, WineModelAssembler wineModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.wineModelAssembler = wineModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<ReducedWine>> fetchWines(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch wines: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<ReducedWine> wines = service.fetchWines(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(wines, wineModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Wine> getWine(@PathVariable Integer id) {
        logger.debug("Fetch wine %d", id);
        
        Optional<Wine> _wine = service.searchWineById(id);
        if (_wine.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        Wine wine = _wine.get();
        logger.debug("Wine found %s", wine);
        wine.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WineController.class).getWine(id)).withSelfRel());
        
        return ResponseEntity.ok(wine);
    }
    
    private void validateFields(Wine wine) {
        if (wine.getName().length() > 128)
            throw new FieldTooLongException(wine.getName().length());
    }
    
    @PostMapping()
    public ResponseEntity<List<Wine>> createWine(@Valid @RequestBody Wine newWine) {
        logger.debug("Creating wine %s...", newWine);
        
        if (service.existsByName(newWine.getName()))
            throw new ObjectExistsException(newWine.getName());
        
        validateFields(newWine);
        
        Wine wine = service.saveWine(newWine);
        return ResponseEntity.created(self.slash(wine.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Wine> updateWine(@Valid @RequestBody Wine newWine, @PathVariable Integer id) {
        logger.debug("Update wine %d", id);
        
        Optional<Wine> _wine = service.searchWineById(id);
        if (_wine.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        Wine wine = _wine.get();
        logger.debug("Wine found %s", wine);
        logger.debug("Wine update %s", newWine);
        
        GeneralUtilities.copyAToB(newWine, wine);
        validateFields(wine);
        service.saveWine(wine);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWine(@PathVariable Integer id) {
        logger.debug("Deleting wine %d", id);
        
        if (service.existsById(id)) {
            service.dropWine(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("La bodega", id);
    }
}
