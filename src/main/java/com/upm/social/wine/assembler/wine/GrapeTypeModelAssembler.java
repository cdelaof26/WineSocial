package com.upm.social.wine.assembler.wine;

import com.upm.social.wine.controller.wine.GrapeTypeController;
import com.upm.social.wine.entity.wine.GrapeType;
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
public class GrapeTypeModelAssembler extends RepresentationModelAssemblerSupport<GrapeType, GrapeType> {
    public GrapeTypeModelAssembler() {
        super(GrapeTypeController.class, GrapeType.class);
    }
    
    @Override
    public GrapeType toModel(GrapeType entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(GrapeTypeController.class).getGrapeType(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
