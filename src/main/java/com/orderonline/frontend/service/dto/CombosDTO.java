package com.orderonline.frontend.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Combos entity.
 */
public class CombosDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean available;

    private Set<OffersDTO> offers = new HashSet<>();

    private Long restaurantId;

    private Long combosId;

    private String combosName;

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

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<OffersDTO> getOffers() {
        return offers;
    }

    public void setOffers(Set<OffersDTO> offers) {
        this.offers = offers;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCombosId() {
        return combosId;
    }

    public void setCombosId(Long ordersId) {
        this.combosId = ordersId;
    }

    public String getCombosName() {
        return combosName;
    }

    public void setCombosName(String ordersName) {
        this.combosName = ordersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CombosDTO combosDTO = (CombosDTO) o;
        if (combosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), combosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CombosDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", available='" + isAvailable() + "'" +
            ", restaurant=" + getRestaurantId() +
            ", combos=" + getCombosId() +
            ", combos='" + getCombosName() + "'" +
            "}";
    }
}
