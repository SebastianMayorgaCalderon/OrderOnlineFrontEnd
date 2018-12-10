package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.Offers;
import com.orderonline.frontend.domain.Restaurant;
import com.orderonline.frontend.repository.OffersRepository;
import com.orderonline.frontend.security.SecurityUtils;
import com.orderonline.frontend.service.dto.OffersDTO;
import com.orderonline.frontend.service.mapper.OffersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Offers.
 */
@Service
@Transactional
public class OffersService {

    private final Logger log = LoggerFactory.getLogger(OffersService.class);

    private final OffersRepository offersRepository;

    private final OffersMapper offersMapper;

    private final RestaurantService restaurantService;

    public OffersService(OffersRepository offersRepository, OffersMapper offersMapper, RestaurantService restaurantService) {
        this.offersRepository = offersRepository;
        this.offersMapper = offersMapper;
        this.restaurantService = restaurantService;
    }

    /**
     * Save a offers.
     *
     * @param offersDTO the entity to save
     * @return the persisted entity
     */
    public OffersDTO save(OffersDTO offersDTO) {
        log.debug("Request to save Offers : {}", offersDTO);
        Restaurant restaurant = this.restaurantService.findOneByUser(SecurityUtils.getCurrentUserLogin().orElse(null));
        Offers offers = offersMapper.toEntity(offersDTO);
        offers.setRestaurant(restaurant);
        offers = offersRepository.save(offers);
        return offersMapper.toDto(offers);
    }

    /**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OffersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        Restaurant restaurant = this.restaurantService.findOneByUser(SecurityUtils.getCurrentUserLogin().orElse(null));
        return offersRepository.findAllByRestaurant(pageable, restaurant)
            .map(offersMapper::toDto);
    }

    /**
     * Get all the Offers with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<OffersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return offersRepository.findAllWithEagerRelationships(pageable).map(offersMapper::toDto);
    }
    

    /**
     * Get one offers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OffersDTO> findOne(Long id) {
        log.debug("Request to get Offers : {}", id);
        return offersRepository.findOneWithEagerRelationships(id)
            .map(offersMapper::toDto);
    }

    /**
     * Delete the offers by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Offers : {}", id);
        offersRepository.deleteById(id);
    }
}
