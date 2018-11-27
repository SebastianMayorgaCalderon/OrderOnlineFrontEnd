package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.DishesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dishes and its DTO DishesDTO.
 */
@Mapper(componentModel = "spring", uses = {CombosMapper.class, OffersMapper.class, RestaurantMapper.class, CategoryMapper.class, OrdersMapper.class})
public interface DishesMapper extends EntityMapper<DishesDTO, Dishes> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "dishes.id", target = "dishesId")
    @Mapping(source = "dishes.name", target = "dishesName")
    DishesDTO toDto(Dishes dishes);

    @Mapping(target = "pricePerProducts", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "dishesId", target = "dishes")
    Dishes toEntity(DishesDTO dishesDTO);

    default Dishes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dishes dishes = new Dishes();
        dishes.setId(id);
        return dishes;
    }
}
