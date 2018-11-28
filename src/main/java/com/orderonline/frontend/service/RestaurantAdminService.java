package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.RestaurantAdmin;
import com.orderonline.frontend.repository.RestaurantAdminRepository;
import com.orderonline.frontend.service.dto.RestaurantAdminDTO;
import com.orderonline.frontend.service.mapper.RestaurantAdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RestaurantAdmin.
 */
@Service
@Transactional
public class RestaurantAdminService {

    private final Logger log = LoggerFactory.getLogger(RestaurantAdminService.class);

    private final RestaurantAdminRepository restaurantAdminRepository;

    private final RestaurantAdminMapper restaurantAdminMapper;

    public RestaurantAdminService(RestaurantAdminRepository restaurantAdminRepository, RestaurantAdminMapper restaurantAdminMapper) {
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.restaurantAdminMapper = restaurantAdminMapper;
    }

    /**
     * Save a restaurantAdmin.
     *
     * @param restaurantAdminDTO the entity to save
     * @return the persisted entity
     */
    public RestaurantAdminDTO save(RestaurantAdminDTO restaurantAdminDTO) {
        log.debug("Request to save RestaurantAdmin : {}", restaurantAdminDTO);

        RestaurantAdmin restaurantAdmin = restaurantAdminMapper.toEntity(restaurantAdminDTO);
        restaurantAdmin = restaurantAdminRepository.save(restaurantAdmin);
        return restaurantAdminMapper.toDto(restaurantAdmin);
    }

    /**
     * Get all the restaurantAdmins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RestaurantAdminDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RestaurantAdmins");
        return restaurantAdminRepository.findAll(pageable)
            .map(restaurantAdminMapper::toDto);
    }


    /**
     * Get one restaurantAdmin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RestaurantAdminDTO> findOne(Long id) {
        log.debug("Request to get RestaurantAdmin : {}", id);
        return restaurantAdminRepository.findById(id)
            .map(restaurantAdminMapper::toDto);
    }

    /**
     * Delete the restaurantAdmin by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RestaurantAdmin : {}", id);
        restaurantAdminRepository.deleteById(id);
    }
    public RestaurantAdminDTO toDto(RestaurantAdmin restaurantAdmin){
        return this.restaurantAdminMapper.toDto(restaurantAdmin);
    }
    public RestaurantAdmin toEntity(RestaurantAdminDTO restaurantAdminDTO){
        return this.restaurantAdminMapper.toEntity(restaurantAdminDTO);
    }
}
