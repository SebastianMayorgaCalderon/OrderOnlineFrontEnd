package com.orderonline.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Combos.
 */
@Entity
@Table(name = "combos")
public class Combos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @OneToMany(mappedBy = "productCombo")
    private Set<PricePerProduct> pricePerProducts = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "combos_dishes",
               joinColumns = @JoinColumn(name = "combos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dishes_id", referencedColumnName = "id"))
    private Set<Dishes> dishes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("combos")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("combos")
    private Orders combos;

    @ManyToMany(mappedBy = "combos")
    @JsonIgnore
    private Set<Offers> offers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Combos name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Combos available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<PricePerProduct> getPricePerProducts() {
        return pricePerProducts;
    }

    public Combos pricePerProducts(Set<PricePerProduct> pricePerProducts) {
        this.pricePerProducts = pricePerProducts;
        return this;
    }

    public Combos addPricePerProduct(PricePerProduct pricePerProduct) {
        this.pricePerProducts.add(pricePerProduct);
        pricePerProduct.setProductCombo(this);
        return this;
    }

    public Combos removePricePerProduct(PricePerProduct pricePerProduct) {
        this.pricePerProducts.remove(pricePerProduct);
        pricePerProduct.setProductCombo(null);
        return this;
    }

    public void setPricePerProducts(Set<PricePerProduct> pricePerProducts) {
        this.pricePerProducts = pricePerProducts;
    }

    public Set<Dishes> getDishes() {
        return dishes;
    }

    public Combos dishes(Set<Dishes> dishes) {
        this.dishes = dishes;
        return this;
    }

    public Combos addDishes(Dishes dishes) {
        this.dishes.add(dishes);
        dishes.getCombos().add(this);
        return this;
    }

    public Combos removeDishes(Dishes dishes) {
        this.dishes.remove(dishes);
        dishes.getCombos().remove(this);
        return this;
    }

    public void setDishes(Set<Dishes> dishes) {
        this.dishes = dishes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Combos restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Orders getCombos() {
        return combos;
    }

    public Combos combos(Orders orders) {
        this.combos = orders;
        return this;
    }

    public void setCombos(Orders orders) {
        this.combos = orders;
    }

    public Set<Offers> getOffers() {
        return offers;
    }

    public Combos offers(Set<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public Combos addOffer(Offers offers) {
        this.offers.add(offers);
        offers.getCombos().add(this);
        return this;
    }

    public Combos removeOffer(Offers offers) {
        this.offers.remove(offers);
        offers.getCombos().remove(this);
        return this;
    }

    public void setOffers(Set<Offers> offers) {
        this.offers = offers;
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
        Combos combos = (Combos) o;
        if (combos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), combos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Combos{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", available='" + isAvailable() + "'" +
            "}";
    }
}
