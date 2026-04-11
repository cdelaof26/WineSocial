package com.upm.social.wine.service.wine;

import com.upm.social.wine.entity.wine.WineType;
import com.upm.social.wine.repository.wine.WineTypeRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Wine type service
 * @author cristopher
 */
@Service
public class WineTypeService {
    private final WineTypeRepository repository;
    
    public WineTypeService(WineTypeRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Fetches a wine type given its ID
     * @param id the id
     * @return a wine type wrapped in Optional object
     */
    public Optional<WineType> searchWineTypeById(Integer id) {
        return repository.findById(id);
    }
    
    /**
     * Fetches a wine type given its id
     * @param id the id
     * @return whether a wine type exists or not
     */
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    /**
     * Fetches a wine type given its name
     * @param name the name
     * @return whether a wine type exists or not
     */
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
    
    /**
     * Returns a page of wineries
     * @param startsWith a string with which the wine type's name should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the wineries in the page
     */
    public Page<WineType> fetchWineTypes(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByNameStartsWith(startsWith, paginable);
        
        return repository.findAll(paginable);
    }
    
    /**
     * Saves a wine type
     * @param wineType the wine type
     * @return a new wine type object taken from the db
     */
    public WineType saveWineType(WineType wineType) {
        return repository.save(wineType);
    }
    
    /**
     * Removes a wine type given its id
     * @param id the id
     */
    public void dropWineType(Integer id) {
        repository.deleteById(id);
    }
}
