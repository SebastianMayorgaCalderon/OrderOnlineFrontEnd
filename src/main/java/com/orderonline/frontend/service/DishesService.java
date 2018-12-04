package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.Dishes;
import com.orderonline.frontend.repository.DishesRepository;
import com.orderonline.frontend.service.dto.DishesDTO;
import com.orderonline.frontend.service.mapper.DishesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Dishes.
 */
@Service
@Transactional
public class DishesService {

    private final Logger log = LoggerFactory.getLogger(DishesService.class);

    private final DishesRepository dishesRepository;

    private final DishesMapper dishesMapper;

    public DishesService(DishesRepository dishesRepository, DishesMapper dishesMapper) {
        this.dishesRepository = dishesRepository;
        this.dishesMapper = dishesMapper;
    }

    /**
     * Save a dishes.
     *
     * @param dishesDTO the entity to save
     * @return the persisted entity
     */
    public DishesDTO save(DishesDTO dishesDTO) {
        log.debug("Request to save Dishes : {}", dishesDTO);

        Dishes dishes = dishesMapper.toEntity(dishesDTO);
        dishes = dishesRepository.save(dishes);
        return dishesMapper.toDto(dishes);
    }

    /**
     * Get all the dishes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DishesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dishes");
        return dishesRepository.findAll(pageable)
            .map(dishesMapper::toDto);
    }


    /**
     * Get one dishes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DishesDTO> findOne(Long id) {
        log.debug("Request to get Dishes : {}", id);
        return dishesRepository.findById(id)
            .map(dishesMapper::toDto);
    }

    /**
     * Delete the dishes by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dishes : {}", id);
        dishesRepository.deleteById(id);
    }
}
