package com.orderonline.frontend.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Offers entity.
 */
public class OffersDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Long restaurantId;

    private Long offersId;

    private String offersName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getOffersId() {
        return offersId;
    }

    public void setOffersId(Long ordersId) {
        this.offersId = ordersId;
    }

    public String getOffersName() {
        return offersName;
    }

    public void setOffersName(String ordersName) {
        this.offersName = ordersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OffersDTO offersDTO = (OffersDTO) o;
        if (offersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OffersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", offers=" + getOffersId() +
            ", offers='" + getOffersName() + "'" +
            "}";
    }
}
