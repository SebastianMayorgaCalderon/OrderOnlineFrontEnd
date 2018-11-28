package com.orderonline.frontend.service.mapper;

import com.orderonline.frontend.domain.*;
import com.orderonline.frontend.service.dto.PricePerProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PricePerProduct and its DTO PricePerProductDTO.
 */
@Mapper(componentModel = "spring", uses = {DishesMapper.class, CombosMapper.class})
public interface PricePerProductMapper extends EntityMapper<PricePerProductDTO, PricePerProduct> {

    @Mapping(source = "productDish.id", target = "productDishId")
    @Mapping(source = "productCombo.id", target = "productComboId")
    PricePerProductDTO toDto(PricePerProduct pricePerProduct);

    @Mapping(source = "productDishId", target = "productDish")
    @Mapping(source = "productComboId", target = "productCombo")
    PricePerProduct toEntity(PricePerProductDTO pricePerProductDTO);

    default PricePerProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        PricePerProduct pricePerProduct = new PricePerProduct();
        pricePerProduct.setId(id);
        return pricePerProduct;
    }
}
