package com.upm.social.wine.service;

import com.upm.social.wine.entity.ReducedUser;
import com.upm.social.wine.repository.UserRepository;
import java.util.Optional;
import com.upm.social.wine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * User service
 * @author cristopher
 */
@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Fetches an user given its ID
     * @param id the id
     * @return an user wrapped in Optional object
     */
    public Optional<User> searchUserById(Integer id) {
        return repository.findById(id);
    }
    
    /**
     * Fetches an user given its id
     * @param id the id
     * @return whether an user exists or not
     */
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    /**
     * Fetches an user given its email
     * @param email the email
     * @return whether an user exists or not
     */
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    
    /**
     * Returns a page of users
     * @param startsWith a string with which the username should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the users in the page
     */
    public Page<ReducedUser> fetchUsers(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByUsernameStartsWith(startsWith, paginable)
                    .map(u -> new ReducedUser(u.getId(), u.getUsername()));
        
        return repository.findAll(paginable)
                .map(u -> new ReducedUser(u.getId(), u.getUsername()));
    }
    
    /**
     * Saves a user
     * @param user the user
     * @return a new user object taken from the db
     */
    public User saveUser(User user) {
        return repository.save(user);
    }
    
    /**
     * Removes a user given its id
     * @param id the id
     */
    public void dropUser(Integer id) {
        repository.deleteById(id);
    }
}
