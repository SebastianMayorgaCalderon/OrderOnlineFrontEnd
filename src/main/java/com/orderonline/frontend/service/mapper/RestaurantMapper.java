package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.RestaurantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Restaurant and its DTO RestaurantDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantAdminMapper.class})
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {

    @Mapping(source = "restaurantAdmin.id", target = "restaurantAdminId")
    @Mapping(source = "restaurantAdmin.name", target = "restaurantAdminName")
    RestaurantDTO toDto(Restaurant restaurant);

    @Mapping(source = "restaurantAdminId", target = "restaurantAdmin")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "combos", ignore = true)
    @Mapping(target = "offers", ignore = true)
    Restaurant toEntity(RestaurantDTO restaurantDTO);

    default Restaurant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
