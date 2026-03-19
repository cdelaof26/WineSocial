package com.upm.social.wine.repository;

import com.upm.social.wine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * User repository
 * @author cristopher
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Returns a page of users
     * @param startsWith a string with which the username should begin with
     * @param pageable the page information
     * @return 
     */
    public Page<User> findByUsernameStartsWith(@Param("startsWith") String startsWith, Pageable pageable);
    
    /**
     * Checks if a user exist given its email
     * @param email the email
     * @return whether it exists or not
     */
    public boolean existsByEmail(String email);
}
