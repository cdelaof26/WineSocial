package com.upm.social.wine.assembler.wine;

import com.upm.social.wine.controller.wine.WineryController;
import com.upm.social.wine.entity.wine.Winery;
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
public class WineryModelAssembler extends RepresentationModelAssemblerSupport<Winery, Winery> {
    public WineryModelAssembler() {
        super(WineryController.class, Winery.class);
    }
    
    @Override
    public Winery toModel(Winery entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(WineryController.class).getWinery(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
