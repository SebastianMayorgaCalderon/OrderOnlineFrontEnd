package com.orderonline.frontend.repository;

import com.orderonline.frontend.domain.Offers;
import com.orderonline.frontend.domain.Restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Offers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

    @Query(value = "select distinct offers from Offers offers left join fetch offers.dishes left join fetch offers.combos",
        countQuery = "select count(distinct offers) from Offers offers")
    Page<Offers> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct offers from Offers offers left join fetch offers.dishes left join fetch offers.combos")
    List<Offers> findAllWithEagerRelationships();

    @Query("select offers from Offers offers left join fetch offers.dishes left join fetch offers.combos where offers.id =:id")
    Optional<Offers> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Offers> findAllByRestaurant(Pageable pageable,Restaurant restaurant);
}
