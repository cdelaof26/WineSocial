package com.upm.social.wine.repository.wine;

import com.upm.social.wine.entity.wine.Winery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Winery repository
 * @author cristopher
 */
public interface WineryRepository extends JpaRepository<Winery, Integer> {
    /**
     * Returns a page of wineries
     * @param startsWith a string with which the username should begin with
     * @param pageable the page information
     * @return 
     */
    public Page<Winery> findByNameStartsWith(@Param("startsWith") String startsWith, Pageable pageable);
    
    /**
     * Checks if a winery exist given its name
     * @param name the name
     * @return whether it exists or not
     */
    public boolean existsByName(String name);
}
