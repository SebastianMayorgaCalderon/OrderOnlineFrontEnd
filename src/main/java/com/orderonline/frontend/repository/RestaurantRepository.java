package com.orderonline.frontend.repository;

import java.util.Optional;

import com.orderonline.frontend.domain.Restaurant;
import com.orderonline.frontend.domain.RestaurantAdmin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findOneByRestaurantAdmin(RestaurantAdmin restaurantAdmin);
}
