package com.orderonline.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @NotNull
    @Column(name = "sub_total_price", nullable = false)
    private Double subTotalPrice;

    @NotNull
    @Column(name = "ivi", nullable = false)
    private Double ivi;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @OneToMany(mappedBy = "dishes")
    private Set<Dishes> dishes = new HashSet<>();
    @OneToMany(mappedBy = "combos")
    private Set<Combos> combos = new HashSet<>();
    @OneToMany(mappedBy = "offers")
    private Set<Offers> offers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Orders totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSubTotalPrice() {
        return subTotalPrice;
    }

    public Orders subTotalPrice(Double subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
        return this;
    }

    public void setSubTotalPrice(Double subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public Double getIvi() {
        return ivi;
    }

    public Orders ivi(Double ivi) {
        this.ivi = ivi;
        return this;
    }

    public void setIvi(Double ivi) {
        this.ivi = ivi;
    }

    public Instant getDate() {
        return date;
    }

    public Orders date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Orders available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Dishes> getDishes() {
        return dishes;
    }

    public Orders dishes(Set<Dishes> dishes) {
        this.dishes = dishes;
        return this;
    }

    public Orders addDishes(Dishes dishes) {
        this.dishes.add(dishes);
        dishes.setDishes(this);
        return this;
    }

    public Orders removeDishes(Dishes dishes) {
        this.dishes.remove(dishes);
        dishes.setDishes(null);
        return this;
    }

    public void setDishes(Set<Dishes> dishes) {
        this.dishes = dishes;
    }

    public Set<Combos> getCombos() {
        return combos;
    }

    public Orders combos(Set<Combos> combos) {
        this.combos = combos;
        return this;
    }

    public Orders addCombos(Combos combos) {
        this.combos.add(combos);
        combos.setCombos(this);
        return this;
    }

    public Orders removeCombos(Combos combos) {
        this.combos.remove(combos);
        combos.setCombos(null);
        return this;
    }

    public void setCombos(Set<Combos> combos) {
        this.combos = combos;
    }

    public Set<Offers> getOffers() {
        return offers;
    }

    public Orders offers(Set<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public Orders addOffers(Offers offers) {
        this.offers.add(offers);
        offers.setOffers(this);
        return this;
    }

    public Orders removeOffers(Offers offers) {
        this.offers.remove(offers);
        offers.setOffers(null);
        return this;
    }

    public void setOffers(Set<Offers> offers) {
        this.offers = offers;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Orders restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if (orders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", subTotalPrice=" + getSubTotalPrice() +
            ", ivi=" + getIvi() +
            ", date='" + getDate() + "'" +
            ", available='" + isAvailable() + "'" +
            "}";
    }
}
