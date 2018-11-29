package com.orderonline.frontend.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Orders entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    @NotNull
    private Double totalPrice;

    @NotNull
    private Double subTotalPrice;

    @NotNull
    private Double ivi;

    @NotNull
    private Instant date;

    @NotNull
    private Integer tableNumber;

    private String details;

    @NotNull
    private Boolean available;

    private Long restaurantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(Double subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public Double getIvi() {
        return ivi;
    }

    public void setIvi(Double ivi) {
        this.ivi = ivi;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", subTotalPrice=" + getSubTotalPrice() +
            ", ivi=" + getIvi() +
            ", date='" + getDate() + "'" +
            ", tableNumber=" + getTableNumber() +
            ", details='" + getDetails() + "'" +
            ", available='" + isAvailable() + "'" +
            ", restaurant=" + getRestaurantId() +
            "}";
    }
}
