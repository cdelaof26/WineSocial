package com.upm.social.wine.assembler.wine;

import com.upm.social.wine.controller.wine.PDOController;
import com.upm.social.wine.entity.wine.PDO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * This class extends RepresentationModelAssemblerSupport which is required for
 * Pagination.
 * It converts the Customer Entity to the Customer Model and has the code for it
 * 
 * @author cristopher
 */
@Component
public class PDOModelAssembler extends RepresentationModelAssemblerSupport<PDO, PDO> {
    public PDOModelAssembler() {
        super(PDOController.class, PDO.class);
    }
    
    @Override
    public PDO toModel(PDO entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PDOController.class).getPDO(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
