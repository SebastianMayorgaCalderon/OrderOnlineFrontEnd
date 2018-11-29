package com.orderonline.frontend.repository;

import java.util.Optional;
import com.orderonline.frontend.domain.RestaurantAdmin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RestaurantAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantAdminRepository extends JpaRepository<RestaurantAdmin, Long> {
    Optional<RestaurantAdmin> findOneByUserId(Long id);
}
