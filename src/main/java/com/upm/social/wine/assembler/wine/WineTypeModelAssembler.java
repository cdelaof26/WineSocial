package com.upm.social.wine.assembler.wine;

import com.upm.social.wine.controller.UserController;
import com.upm.social.wine.entity.wine.WineType;
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
public class WineTypeModelAssembler extends RepresentationModelAssemblerSupport<WineType, WineType> {
    public WineTypeModelAssembler() {
        super(UserController.class, WineType.class);
    }
    
    @Override
    public WineType toModel(WineType entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUser(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
