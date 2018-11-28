package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.RestaurantAdminDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RestaurantAdmin and its DTO RestaurantAdminDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RestaurantAdminMapper extends EntityMapper<RestaurantAdminDTO, RestaurantAdmin> {

    @Mapping(source = "user.id", target = "userId")
    RestaurantAdminDTO toDto(RestaurantAdmin restaurantAdmin);

    @Mapping(source = "userId", target = "user")
    RestaurantAdmin toEntity(RestaurantAdminDTO restaurantAdminDTO);

    default RestaurantAdmin fromId(Long id) {
        if (id == null) {
            return null;
        }
        RestaurantAdmin restaurantAdmin = new RestaurantAdmin();
        restaurantAdmin.setId(id);
        return restaurantAdmin;
    }
}
