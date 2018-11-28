package com.orderonline.frontend.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PricePerProduct entity.
 */
public class PricePerProductDTO implements Serializable {

    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private Instant date;

    private Long productDishId;

    private Long productComboId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getProductDishId() {
        return productDishId;
    }

    public void setProductDishId(Long dishesId) {
        this.productDishId = dishesId;
    }

    public Long getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(Long combosId) {
        this.productComboId = combosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PricePerProductDTO pricePerProductDTO = (PricePerProductDTO) o;
        if (pricePerProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pricePerProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PricePerProductDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", date='" + getDate() + "'" +
            ", productDish=" + getProductDishId() +
            ", productCombo=" + getProductComboId() +
            "}";
    }
}
