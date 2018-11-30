package com.orderonline.frontend.repository;

import com.orderonline.frontend.domain.Category;
import com.orderonline.frontend.domain.Restaurant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.boot.devtools.autoconfigure.DevToolsProperties.Restart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByRestaurant(Pageable pageable,Restaurant restaurant);
}
