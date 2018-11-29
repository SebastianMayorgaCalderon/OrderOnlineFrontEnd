package com.orderonline.frontend.service;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.domain.User;
import com.orderonline.frontend.repository.RestaurantAdminRepository;
import com.orderonline.frontend.repository.RestaurantRepository;
import com.orderonline.frontend.repository.UserRepository;
import com.orderonline.frontend.service.dto.RestaurantDTO;
import com.orderonline.frontend.service.mapper.RestaurantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Restaurant.
 */
@Service
@Transactional
public class RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final RestaurantAdminRepository restaurantAdminRepository;

    private final RestaurantMapper restaurantMapper;

    private final RestaurantAdminService restaurantAdminService;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, UserRepository userRepository, RestaurantAdminRepository restaurantAdminRepository, RestaurantAdminService restaurantAdminService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.userRepository = userRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.restaurantAdminService = restaurantAdminService;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurantDTO the entity to save
     * @return the persisted entity
     */
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    /**
     * Get all the restaurants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll(pageable)
            .map(restaurantMapper::toDto);
    }


    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RestaurantDTO> findOne(Long id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id)
            .map(restaurantMapper::toDto);
    }

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
    }

    public Boolean createRestaurantWhenActivated(String activationKey, String restaurantName) {
        if(this.userRepository.findOneByActivationKey(activationKey).isPresent()){
            User user = this.userRepository.findOneByActivationKey(activationKey).orElse(null);
            RestaurantAdmin restaurantAdmin = this.restaurantAdminRepository.findOneByUserId(user.getId()).orElse(null);
            RestaurantDTO restaurant = this.restaurantMapper.toDto(new Restaurant());
            restaurant.setName(restaurantName);
            restaurant.setRestaurantAdminId(restaurantAdmin.getId());
            this.restaurantRepository.save(this.restaurantMapper.toEntity(restaurant));
            return true;
        }
        return false;
    }
    public Restaurant findOneByUser(String login){
        RestaurantAdmin restaurantAdmin = this.restaurantAdminService.frinOneByUser(login);
        return this.restaurantRepository.findOneByRestaurantAdmin(restaurantAdmin).orElse(null);
    }
}
