package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    OrdersDTO toDto(Orders orders);

    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "combos", ignore = true)
    @Mapping(target = "offers", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
