package com.upm.social.wine.controller.wine;

import com.upm.social.wine.assembler.wine.WineryModelAssembler;
import com.upm.social.wine.entity.GeneralUtilities;
import java.util.List;
import com.upm.social.wine.entity.wine.Winery;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.service.wine.WineryService;
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
 * Winery controller
 * @author cristopher
 */
@RestController
@RequestMapping("/wineries")
public class WineryController {
    private final WineryService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(WineryController.class);
    
    private final PagedResourcesAssembler<Winery> pagedResourcesAssembler;
    private final WineryModelAssembler wineryModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(WineryController.class);

    public WineryController(WineryService service, PagedResourcesAssembler<Winery> pagedResourcesAssembler, WineryModelAssembler wineryModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.wineryModelAssembler = wineryModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<Winery>> fetchWinerys(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch wineries: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<Winery> wineries = service.fetchWineries(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(wineries, wineryModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<Winery> getWinery(@PathVariable Integer id) {
        logger.debug("Fetch winery %d", id);
        
        Optional<Winery> _winery = service.searchWineryById(id);
        if (_winery.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        Winery winery = _winery.get();
        logger.debug("Winery found %s", winery);
        winery.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WineryController.class).getWinery(id)).withSelfRel());
        
        return ResponseEntity.ok(winery);
    }
    
    private void validateFields(Winery winery) {
        if (winery.getName().length() > 128)
            throw new FieldTooLongException(winery.getName().length());
        
        if (winery.getPostalAddress() != null && winery.getPostalAddress().length() > 512)
            throw new FieldTooLongException("La dirección postal", winery.getPostalAddress().length(), 512);
    }
    
    @PostMapping()
    public ResponseEntity<List<Winery>> createWinery(@Valid @RequestBody Winery newWinery) {
        logger.debug("Creating winery %s...", newWinery);
        
        if (service.existsByName(newWinery.getName()))
            throw new ObjectExistsException(newWinery.getName());
        
        validateFields(newWinery);
        
        Winery winery = service.saveWinery(newWinery);
        return ResponseEntity.created(self.slash(winery.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Winery> updateWinery(@Valid @RequestBody Winery newWinery, @PathVariable Integer id) {
        logger.debug("Update winery %d", id);
        
        Optional<Winery> _winery = service.searchWineryById(id);
        if (_winery.isEmpty())
            throw new ObjectNotFoundException("La bodega", id);
        
        Winery winery = _winery.get();
        logger.debug("Winery found %s", winery);
        logger.debug("Winery update %s", newWinery);
        
        GeneralUtilities.copyAToB(newWinery, winery);
        validateFields(winery);
        service.saveWinery(winery);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWinery(@PathVariable Integer id) {
        logger.debug("Deleting winery %d", id);
        
        if (service.existsById(id)) {
            service.dropWinery(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("La bodega", id);
    }
}
