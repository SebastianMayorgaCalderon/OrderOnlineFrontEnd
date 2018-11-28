package com.orderonline.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id")
    private Integer userID;

    @OneToOne    @JoinColumn(unique = true)
    private RestaurantAdmin restaurantAdmin;

    @OneToMany(mappedBy = "restaurant")
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<Dishes> dishes = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<Orders> orders = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
    private Set<Combos> combos = new HashSet<>();
    @OneToMany(mappedBy = "restaurant")
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

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserID() {
        return userID;
    }

    public Restaurant userID(Integer userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public RestaurantAdmin getRestaurantAdmin() {
        return restaurantAdmin;
    }

    public Restaurant restaurantAdmin(RestaurantAdmin restaurantAdmin) {
        this.restaurantAdmin = restaurantAdmin;
        return this;
    }

    public void setRestaurantAdmin(RestaurantAdmin restaurantAdmin) {
        this.restaurantAdmin = restaurantAdmin;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Restaurant categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Restaurant addCategory(Category category) {
        this.categories.add(category);
        category.setRestaurant(this);
        return this;
    }

    public Restaurant removeCategory(Category category) {
        this.categories.remove(category);
        category.setRestaurant(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Dishes> getDishes() {
        return dishes;
    }

    public Restaurant dishes(Set<Dishes> dishes) {
        this.dishes = dishes;
        return this;
    }

    public Restaurant addDishes(Dishes dishes) {
        this.dishes.add(dishes);
        dishes.setRestaurant(this);
        return this;
    }

    public Restaurant removeDishes(Dishes dishes) {
        this.dishes.remove(dishes);
        dishes.setRestaurant(null);
        return this;
    }

    public void setDishes(Set<Dishes> dishes) {
        this.dishes = dishes;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public Restaurant orders(Set<Orders> orders) {
        this.orders = orders;
        return this;
    }

    public Restaurant addOrders(Orders orders) {
        this.orders.add(orders);
        orders.setRestaurant(this);
        return this;
    }

    public Restaurant removeOrders(Orders orders) {
        this.orders.remove(orders);
        orders.setRestaurant(null);
        return this;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<Combos> getCombos() {
        return combos;
    }

    public Restaurant combos(Set<Combos> combos) {
        this.combos = combos;
        return this;
    }

    public Restaurant addCombos(Combos combos) {
        this.combos.add(combos);
        combos.setRestaurant(this);
        return this;
    }

    public Restaurant removeCombos(Combos combos) {
        this.combos.remove(combos);
        combos.setRestaurant(null);
        return this;
    }

    public void setCombos(Set<Combos> combos) {
        this.combos = combos;
    }

    public Set<Offers> getOffers() {
        return offers;
    }

    public Restaurant offers(Set<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public Restaurant addOffers(Offers offers) {
        this.offers.add(offers);
        offers.setRestaurant(this);
        return this;
    }

    public Restaurant removeOffers(Offers offers) {
        this.offers.remove(offers);
        offers.setRestaurant(null);
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
        Restaurant restaurant = (Restaurant) o;
        if (restaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", userID=" + getUserID() +
            "}";
    }
}
