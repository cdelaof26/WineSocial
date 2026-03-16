package com.upm.social.wine.service;

import com.upm.social.wine.repository.UserRepository;
import java.util.Optional;
import com.upm.social.wine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The user service
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
     * Returns a page of users
     * @param startsWith a string with which the username should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the users in the page
     */
    public Page<User> fetchUsers(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByUsernameStartsWith(startsWith, paginable);
        
        return repository.findAll(paginable);
    }
}
