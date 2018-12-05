package com.orderonline.frontend.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Dishes entity.
 */
public class DishesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Boolean available;

    
    @Lob
    private byte[] image;
    private String imageContentType;

    private Long restaurantId;

    private Long categoryId;

    private String categoryName;

    private Long dishesId;

    private String dishesName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getDishesId() {
        return dishesId;
    }

    public void setDishesId(Long ordersId) {
        this.dishesId = ordersId;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String ordersName) {
        this.dishesName = ordersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishesDTO dishesDTO = (DishesDTO) o;
        if (dishesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", available='" + isAvailable() + "'" +
            ", image='" + getImage() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", category=" + getCategoryId() +
            ", category='" + getCategoryName() + "'" +
            ", dishes=" + getDishesId() +
            ", dishes='" + getDishesName() + "'" +
            "}";
    }
}
