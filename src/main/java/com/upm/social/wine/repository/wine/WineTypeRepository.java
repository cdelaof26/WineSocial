package com.upm.social.wine.repository.wine;

import com.upm.social.wine.entity.wine.PDO;
import com.upm.social.wine.entity.wine.WineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Wine type repository
 * @author cristopher
 */
public interface WineTypeRepository extends JpaRepository<WineType, Integer> {
    /**
     * Returns a page of wine types
     * @param startsWith a string with which the username should begin with
     * @param pageable the page information
     * @return 
     */
    public Page<WineType> findByNameStartsWith(@Param("startsWith") String startsWith, Pageable pageable);
    
    /**
     * Checks if a wine type exist given its name
     * @param name the name
     * @return whether it exists or not
     */
    public boolean existsByName(String name);
}
