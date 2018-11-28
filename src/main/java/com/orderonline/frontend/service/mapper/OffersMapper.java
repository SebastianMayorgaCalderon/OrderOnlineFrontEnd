package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.OffersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Offers and its DTO OffersDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class, OrdersMapper.class})
public interface OffersMapper extends EntityMapper<OffersDTO, Offers> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "offers.id", target = "offersId")
    @Mapping(source = "offers.name", target = "offersName")
    OffersDTO toDto(Offers offers);

    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "offersId", target = "offers")
    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "combos", ignore = true)
    Offers toEntity(OffersDTO offersDTO);

    default Offers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offers offers = new Offers();
        offers.setId(id);
        return offers;
    }
}
