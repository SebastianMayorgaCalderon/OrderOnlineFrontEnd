package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.CombosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Combos and its DTO CombosDTO.
 */
@Mapper(componentModel = "spring", uses = {DishesMapper.class, RestaurantMapper.class, OrdersMapper.class})
public interface CombosMapper extends EntityMapper<CombosDTO, Combos> {

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "combos.id", target = "combosId")
    @Mapping(source = "combos.name", target = "combosName")
    CombosDTO toDto(Combos combos);

    @Mapping(target = "pricePerProducts", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant")
    @Mapping(source = "combosId", target = "combos")
    @Mapping(target = "offers", ignore = true)
    Combos toEntity(CombosDTO combosDTO);

    default Combos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Combos combos = new Combos();
        combos.setId(id);
        return combos;
    }
}
