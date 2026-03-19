package com.upm.social.wine.assembler;

import com.upm.social.wine.controller.UserController;
import com.upm.social.wine.entity.ReducedUser;
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
public class UserModelAssembler extends RepresentationModelAssemblerSupport<ReducedUser, ReducedUser> {
    public UserModelAssembler() {
        super(UserController.class, ReducedUser.class);
    }
    
    @Override
    public ReducedUser toModel(ReducedUser entity) {
        entity.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUser(entity.getId())
            ).withSelfRel()
        );

        return entity;
    }
}
