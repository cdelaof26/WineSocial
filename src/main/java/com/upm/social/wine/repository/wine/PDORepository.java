package com.upm.social.wine.repository.wine;

import com.upm.social.wine.entity.wine.PDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * PDO repository
 * @author cristopher
 */
public interface PDORepository extends JpaRepository<PDO, Integer> {
    /**
     * Returns a page of wineries
     * @param startsWith a string with which the username should begin with
     * @param pageable the page information
     * @return 
     */
    public Page<PDO> findByNameStartsWith(@Param("startsWith") String startsWith, Pageable pageable);
    
    /**
     * Checks if a winery exist given its name
     * @param name the name
     * @return whether it exists or not
     */
    public boolean existsByName(String name);
}
