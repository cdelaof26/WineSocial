package com.upm.social.wine.controller;

import com.upm.social.wine.assembler.UserModelAssembler;
import com.upm.social.wine.exception.UserNotFoundException;
import com.upm.social.wine.service.UserService;
import java.util.List;
import com.upm.social.wine.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 * @author cristopher
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;
    private final UserModelAssembler userModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service, PagedResourcesAssembler<User> pagedResourcesAssembler, UserModelAssembler userModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userModelAssembler = userModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<User>> fetchUsers(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        Page<User> users = service.fetchUsers(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, userModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User user = service.searchUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel());
        
        return ResponseEntity.ok(user);
    }
    
    @PostMapping()
    public ResponseEntity<List<User>> createUser(@RequestBody User newUser) {
        return null;
    }
}
