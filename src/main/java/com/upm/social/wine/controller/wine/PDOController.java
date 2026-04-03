package com.upm.social.wine.controller.wine;

import com.upm.social.wine.assembler.wine.PDOModelAssembler;
import com.upm.social.wine.entity.GeneralUtilities;
import java.util.List;
import com.upm.social.wine.entity.wine.PDO;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.service.wine.PDOService;
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
 * PDO controller
 * @author cristopher
 */
@RestController
@RequestMapping("/pdos")
public class PDOController {
    private final PDOService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(PDOController.class);
    
    private final PagedResourcesAssembler<PDO> pagedResourcesAssembler;
    private final PDOModelAssembler pdoModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(PDOController.class);

    public PDOController(PDOService service, PagedResourcesAssembler<PDO> pagedResourcesAssembler, PDOModelAssembler pdoModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pdoModelAssembler = pdoModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<PDO>> fetchPDOs(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch pdos: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<PDO> pdos = service.fetchWineries(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pdos, pdoModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<PDO> getPDO(@PathVariable Integer id) {
        logger.debug("Fetch pdo %d", id);
        
        Optional<PDO> _pdo = service.searchPDOById(id);
        if (_pdo.isEmpty())
            throw new ObjectNotFoundException("La denominación de origen", id);
        
        PDO pdo = _pdo.get();
        logger.debug("PDO found %s", pdo);
        pdo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PDOController.class).getPDO(id)).withSelfRel());
        
        return ResponseEntity.ok(pdo);
    }
    
    private void validateFields(PDO pdo) {
        if (pdo.getName().length() > 128)
            throw new FieldTooLongException(pdo.getName().length());
        
        if (pdo.getPostalAddress() != null && pdo.getPostalAddress().length() > 512)
            throw new FieldTooLongException("La dirección postal", pdo.getPostalAddress().length(), 512);
    }
    
    @PostMapping()
    public ResponseEntity<List<PDO>> createPDO(@Valid @RequestBody PDO newPDO) {
        logger.debug("Creating pdo %s...", newPDO);
        
        if (service.existsByName(newPDO.getName()))
            throw new ObjectExistsException(newPDO.getName());
        
        validateFields(newPDO);
        
        PDO pdo = service.savePDO(newPDO);
        return ResponseEntity.created(self.slash(pdo.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<PDO> updatePDO(@Valid @RequestBody PDO newPDO, @PathVariable Integer id) {
        logger.debug("Update pdo %d", id);
        
        Optional<PDO> _pdo = service.searchPDOById(id);
        if (_pdo.isEmpty())
            throw new ObjectNotFoundException("La denominación de origen", id);
        
        PDO pdo = _pdo.get();
        logger.debug("PDO found %s", pdo);
        logger.debug("PDO update %s", newPDO);
        
        GeneralUtilities.copyAToB(newPDO, pdo);
        validateFields(pdo);
        service.savePDO(pdo);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePDO(@PathVariable Integer id) {
        logger.debug("Deleting pdo %d", id);
        
        if (service.existsById(id)) {
            service.dropPDO(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("La denominación de origen", id);
    }
}
