package com.tuto.spatial.service;

import com.tuto.spatial.domain.Place;
import com.tuto.spatial.repository.PlaceRepository;
import com.tuto.spatial.repository.search.PlaceSearchRepository;
import com.tuto.spatial.service.dto.PlaceDTO;
import com.tuto.spatial.service.mapper.PlaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Place.
 */
@Service
@Transactional
public class PlaceService {

    private final Logger log = LoggerFactory.getLogger(PlaceService.class);
    
    private final PlaceRepository placeRepository;

    private final PlaceMapper placeMapper;

    private final PlaceSearchRepository placeSearchRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper, PlaceSearchRepository placeSearchRepository) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.placeSearchRepository = placeSearchRepository;
    }

    /**
     * Save a place.
     *
     * @param placeDTO the entity to save
     * @return the persisted entity
     */
    public PlaceDTO save(PlaceDTO placeDTO) {
        log.debug("Request to save Place : {}", placeDTO);
        Place place = placeMapper.toEntity(placeDTO);
        place = placeRepository.save(place);
        PlaceDTO result = placeMapper.toDto(place);
        placeSearchRepository.save(place);
        return result;
    }

    /**
     *  Get all the places.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        Page<Place> result = placeRepository.findAll(pageable);
        return result.map(place -> placeMapper.toDto(place));
    }

    /**
     *  Get one place by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PlaceDTO findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        Place place = placeRepository.findOne(id);
        PlaceDTO placeDTO = placeMapper.toDto(place);
        return placeDTO;
    }

    /**
     *  Delete the  place by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Place : {}", id);
        placeRepository.delete(id);
        placeSearchRepository.delete(id);
    }

    /**
     * Search for the place corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PlaceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Places for query {}", query);
        Page<Place> result = placeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(place -> placeMapper.toDto(place));
    }
}
