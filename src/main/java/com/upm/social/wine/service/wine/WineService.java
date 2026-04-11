package com.upm.social.wine.service.wine;

import com.upm.social.wine.entity.wine.ReducedWine;
import com.upm.social.wine.entity.wine.Wine;
import com.upm.social.wine.repository.wine.WineRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Wine service
 * @author cristopher
 */
@Service
public class WineService {
    private final WineRepository repository;
    
    public WineService(WineRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Fetches a wine given its ID
     * @param id the id
     * @return a wine wrapped in Optional object
     */
    public Optional<Wine> searchWineById(Integer id) {
        return repository.findById(id);
    }
    
    /**
     * Fetches a wine given its id
     * @param id the id
     * @return whether a wine exists or not
     */
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    /**
     * Fetches a wine given its name
     * @param name the name
     * @return whether a wine exists or not
     */
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
    
    /**
     * Returns a page of wineries
     * @param startsWith a string with which the wine's name should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the wineries in the page
     */
    public Page<ReducedWine> fetchWines(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByNameStartsWith(startsWith, paginable).map(
                w -> new ReducedWine(
                    w.getId(), w.getName(), w.getWinery().getName(), 
                    w.getVintage(), w.getPdo().getName(), w.getWinetype().getName()
                )
            );
        
        return repository.findAll(paginable).map(
            w -> new ReducedWine(
                w.getId(), w.getName(), w.getWinery().getName(), 
                w.getVintage(), w.getPdo().getName(), w.getWinetype().getName()
            )
        );
    }
    
    /**
     * Saves a wine
     * @param wine the wine
     * @return a new wine object taken from the db
     */
    public Wine saveWine(Wine wine) {
        return repository.save(wine);
    }
    
    /**
     * Removes a wine given its id
     * @param id the id
     */
    public void dropWine(Integer id) {
        repository.deleteById(id);
    }
}
