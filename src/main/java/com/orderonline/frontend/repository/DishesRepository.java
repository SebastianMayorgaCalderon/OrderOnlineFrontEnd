package com.orderonline.frontend.repository;

import com.orderonline.frontend.domain.Dishes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dishes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishesRepository extends JpaRepository<Dishes, Long> {

    @Query(value = "select distinct dishes from Dishes dishes left join fetch dishes.combos left join fetch dishes.offers",
        countQuery = "select count(distinct dishes) from Dishes dishes")
    Page<Dishes> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct dishes from Dishes dishes left join fetch dishes.combos left join fetch dishes.offers")
    List<Dishes> findAllWithEagerRelationships();

    @Query("select dishes from Dishes dishes left join fetch dishes.combos left join fetch dishes.offers where dishes.id =:id")
    Optional<Dishes> findOneWithEagerRelationships(@Param("id") Long id);

}
