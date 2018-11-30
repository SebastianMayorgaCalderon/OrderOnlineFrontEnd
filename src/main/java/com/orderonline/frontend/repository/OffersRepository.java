package com.orderonline.frontend.repository;

import com.orderonline.frontend.domain.Offers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Offers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

}
