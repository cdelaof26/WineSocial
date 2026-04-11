package com.upm.social.wine.assembler.wine;

import com.upm.social.wine.controller.wine.WineController;
import com.upm.social.wine.entity.wine.ReducedWine;
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
public class WineModelAssembler extends RepresentationModelAssemblerSupport<ReducedWine, ReducedWine> {
    public WineModelAssembler() {
        super(WineController.class, ReducedWine.class);
    }
    
    @Override
    public ReducedWine toModel(ReducedWine entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(WineController.class).getWine(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
