package com.upm.social.wine.controller;

import com.upm.social.wine.assembler.UserModelAssembler;
import com.upm.social.wine.entity.ReducedUser;
import com.upm.social.wine.service.UserService;
import java.util.List;
import com.upm.social.wine.entity.User;
import com.upm.social.wine.entity.GeneralUtilities;
import com.upm.social.wine.exception.FieldTooLongException;
import com.upm.social.wine.exception.ObjectExistsException;
import com.upm.social.wine.exception.ObjectNotFoundException;
import com.upm.social.wine.exception.user.InvalidUserAge;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
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
 * User controller
 * @author cristopher
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(UserController.class);
    
    private final PagedResourcesAssembler<ReducedUser> pagedResourcesAssembler;
    private final UserModelAssembler userModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service, PagedResourcesAssembler<ReducedUser> pagedResourcesAssembler, UserModelAssembler userModelAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userModelAssembler = userModelAssembler;
    }
    
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<PagedModel<ReducedUser>> fetchUsers(
        @RequestParam(defaultValue = "", required = false) String startsWith,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "2", required = false) int size
    ) {
        logger.debug("Fetch users: %s", self.toUri().toString());
        logger.debug("Starting with %s", startsWith);
        logger.debug("In page %d of %d elements", page, size);
        
        Page<ReducedUser> users = service.fetchUsers(startsWith, page, size);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(users, userModelAssembler));
    }
    
    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        logger.debug("Fetch user %d", id);
        
        Optional<User> _user = service.searchUserById(id);
        if (_user.isEmpty())
            throw new ObjectNotFoundException("El usuario", id);
        
        User user = _user.get();
        logger.debug("User found %s", user);
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel());
        
        return ResponseEntity.ok(user);
    }
    
    private void validateFields(User user) {
        if (user.getUsername().length() > 128)
            throw new FieldTooLongException(user.getUsername().length());
        
        if (user.getEmail().length() > 128)
            throw new FieldTooLongException("El correo", user.getEmail().length());
        
        Period period = Period.between(user.getBirthdate().toLocalDate(), LocalDate.now());
        
        int age = period.getYears();
        if (age < 18)
            throw new InvalidUserAge(age);
    }
    
    @PostMapping()
    public ResponseEntity<List<User>> createUser(@Valid @RequestBody User newUser) {
        logger.debug("Creating user %s...", newUser);
        
        if (service.existsByEmail(newUser.getEmail()))
            throw new ObjectExistsException("El correo", newUser.getEmail());
        
        validateFields(newUser);
        
        User user = service.saveUser(newUser);
        return ResponseEntity.created(self.slash(user.getId()).toUri()).build();
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser, @PathVariable Integer id) {
        logger.debug("Update user %d", id);
        
        Optional<User> _user = service.searchUserById(id);
        if (_user.isEmpty())
            throw new ObjectNotFoundException("El usuario", id);
        
        User user = _user.get();
        logger.debug("User found %s", user);
        logger.debug("User update %s", newUser);
        
        GeneralUtilities.copyAToB(newUser, user);
        validateFields(newUser);
        service.saveUser(user);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        logger.debug("Deleting user %d", id);
        
        if (service.existsById(id)) {
            service.dropUser(id);
            return ResponseEntity.noContent().build();
        }
        
        throw new ObjectNotFoundException("El usuario", id);
    }
}
