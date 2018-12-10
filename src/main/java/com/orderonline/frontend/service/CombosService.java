package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.Combos;
import com.orderonline.frontend.domain.Restaurant;
import com.orderonline.frontend.repository.CombosRepository;
import com.orderonline.frontend.security.SecurityUtils;
import com.orderonline.frontend.service.dto.CombosDTO;
import com.orderonline.frontend.service.mapper.CombosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Combos.
 */
@Service
@Transactional
public class CombosService {

    private final Logger log = LoggerFactory.getLogger(CombosService.class);

    private final CombosRepository combosRepository;

    private final CombosMapper combosMapper;

    private final RestaurantService restaurantService;

    public CombosService(CombosRepository combosRepository, CombosMapper combosMapper, RestaurantService restaurantService) {
        this.combosRepository = combosRepository;
        this.combosMapper = combosMapper;
        this.restaurantService = restaurantService;
    }

    /**
     * Save a combos.
     *
     * @param combosDTO the entity to save
     * @return the persisted entity
     */
    public CombosDTO save(CombosDTO combosDTO) {
        log.debug("Request to save Combos : {}", combosDTO);
        Restaurant restaurant = this.restaurantService.findOneByUser(SecurityUtils.getCurrentUserLogin().orElse(null));
        Combos combos = combosMapper.toEntity(combosDTO);
        combos.setRestaurant(restaurant);
        combos = combosRepository.save(combos);
        return combosMapper.toDto(combos);
    }

    /**
     * Get all the combos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CombosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Combos");
        Restaurant restaurant = this.restaurantService.findOneByUser(SecurityUtils.getCurrentUserLogin().orElse(null));
        return combosRepository.findAllByRestaurant(pageable, restaurant)
            .map(combosMapper::toDto);
    }

    /**
     * Get all the Combos with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CombosDTO> findAllWithEagerRelationships(Pageable pageable) {
        return combosRepository.findAllWithEagerRelationships(pageable).map(combosMapper::toDto);
    }
    

    /**
     * Get one combos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CombosDTO> findOne(Long id) {
        log.debug("Request to get Combos : {}", id);
        return combosRepository.findOneWithEagerRelationships(id)
            .map(combosMapper::toDto);
    }

    /**
     * Delete the combos by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Combos : {}", id);
        combosRepository.deleteById(id);
    }
}
