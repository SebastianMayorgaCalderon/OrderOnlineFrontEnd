package com.orderonline.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Offers.
 */
@Entity
@Table(name = "offers")
public class Offers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @ManyToMany
    @JoinTable(name = "offers_dishes",
               joinColumns = @JoinColumn(name = "offers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dishes_id", referencedColumnName = "id"))
    private Set<Dishes> dishes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "offers_combos",
               joinColumns = @JoinColumn(name = "offers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "combos_id", referencedColumnName = "id"))
    private Set<Combos> combos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Orders offers;

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

    public Offers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Offers price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public Offers image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Offers imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Offers available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Dishes> getDishes() {
        return dishes;
    }

    public Offers dishes(Set<Dishes> dishes) {
        this.dishes = dishes;
        return this;
    }

    public Offers addDishes(Dishes dishes) {
        this.dishes.add(dishes);
        dishes.getOffers().add(this);
        return this;
    }

    public Offers removeDishes(Dishes dishes) {
        this.dishes.remove(dishes);
        dishes.getOffers().remove(this);
        return this;
    }

    public void setDishes(Set<Dishes> dishes) {
        this.dishes = dishes;
    }

    public Set<Combos> getCombos() {
        return combos;
    }

    public Offers combos(Set<Combos> combos) {
        this.combos = combos;
        return this;
    }

    public Offers addCombos(Combos combos) {
        this.combos.add(combos);
        combos.getOffers().add(this);
        return this;
    }

    public Offers removeCombos(Combos combos) {
        this.combos.remove(combos);
        combos.getOffers().remove(this);
        return this;
    }

    public void setCombos(Set<Combos> combos) {
        this.combos = combos;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Offers restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Orders getOffers() {
        return offers;
    }

    public Offers offers(Orders orders) {
        this.offers = orders;
        return this;
    }

    public void setOffers(Orders orders) {
        this.offers = orders;
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
        Offers offers = (Offers) o;
        if (offers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", available='" + isAvailable() + "'" +
            "}";
    }
}
