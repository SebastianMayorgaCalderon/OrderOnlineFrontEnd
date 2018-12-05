package com.orderonline.frontend.repository;

import com.orderonline.frontend.domain.Dishes;
import com.orderonline.frontend.domain.Restaurant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data  repository for the Dishes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {
    Page<Dishes> findAllByRestaurant(Pageable pageable,Restaurant restaurant);
}
