package com.upm.social.wine.service.wine;

import com.upm.social.wine.entity.wine.GrapeType;
import com.upm.social.wine.repository.wine.GrapeTypeRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * GrapeType service
 * @author cristopher
 */
@Service
public class GrapeTypeService {
    private final GrapeTypeRepository repository;
    
    public GrapeTypeService(GrapeTypeRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Fetches a grapeType given its ID
     * @param id the id
     * @return a grapeType wrapped in Optional object
     */
    public Optional<GrapeType> searchGrapeTypeById(Integer id) {
        return repository.findById(id);
    }
    
    /**
     * Fetches a grapeType given its id
     * @param id the id
     * @return whether a grapeType exists or not
     */
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    /**
     * Fetches a grapeType given its name
     * @param name the name
     * @return whether a grapeType exists or not
     */
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
    
    /**
     * Returns a page of grapeTypes
     * @param startsWith a string with which the grapeType's name should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the grapeTypes in the page
     */
    public Page<GrapeType> fetchGrapeTypes(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByNameStartsWith(startsWith, paginable);
        
        return repository.findAll(paginable);
    }
    
    /**
     * Saves a grapeType
     * @param grapeType the grapeType
     * @return a new grapeType object taken from the db
     */
    public GrapeType saveGrapeType(GrapeType grapeType) {
        return repository.save(grapeType);
    }
    
    /**
     * Removes a grapeType given its id
     * @param id the id
     */
    public void dropGrapeType(Integer id) {
        repository.deleteById(id);
    }
}
