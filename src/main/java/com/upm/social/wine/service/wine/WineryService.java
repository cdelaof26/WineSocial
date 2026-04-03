package com.upm.social.wine.service.wine;

import com.upm.social.wine.entity.wine.Winery;
import com.upm.social.wine.repository.wine.WineryRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The winery service
 * @author cristopher
 */
@Service
public class WineryService {
    private final WineryRepository repository;
    
    public WineryService(WineryRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Fetches a winery given its ID
     * @param id the id
     * @return a winery wrapped in Optional object
     */
    public Optional<Winery> searchWineryById(Integer id) {
        return repository.findById(id);
    }
    
    /**
     * Fetches a winery given its id
     * @param id the id
     * @return whether a winery exists or not
     */
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    /**
     * Fetches a winery given its name
     * @param name the name
     * @return whether a winery exists or not
     */
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
    
    /**
     * Returns a page of wineries
     * @param startsWith a string with which the winery's name should begin with
     * @param page the page number
     * @param size the size of the page
     * @return all the wineries in the page
     */
    public Page<Winery> fetchWineries(String startsWith, int page, int size) {
        Pageable paginable = PageRequest.of(page, size);
        
        if (startsWith != null)
            return repository.findByNameStartsWith(startsWith, paginable);
        
        return repository.findAll(paginable);
    }
    
    /**
     * Saves a winery
     * @param winery the winery
     * @return a new winery object taken from the db
     */
    public Winery saveWinery(Winery winery) {
        return repository.save(winery);
    }
    
    /**
     * Removes a winery given its id
     * @param id the id
     */
    public void dropWinery(Integer id) {
        repository.deleteById(id);
    }
}
