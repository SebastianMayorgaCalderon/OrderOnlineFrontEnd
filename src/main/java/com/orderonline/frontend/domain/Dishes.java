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
 * A Dishes.
 */
@Entity
@Table(name = "dishes")
public class Dishes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @OneToMany(mappedBy = "productDish")
    private Set<PricePerProduct> pricePerProducts = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "dishes_combos",
               joinColumns = @JoinColumn(name = "dishes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "combos_id", referencedColumnName = "id"))
    private Set<Combos> combos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "dishes_offer",
               joinColumns = @JoinColumn(name = "dishes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "offers_id", referencedColumnName = "id"))
    private Set<Offers> offers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Restaurant restaurant;

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Orders dishes;

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

    public Dishes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Dishes description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAvailable() {
        return available;
    }

    public Dishes available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public byte[] getImage() {
        return image;
    }

    public Dishes image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Dishes imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<PricePerProduct> getPricePerProducts() {
        return pricePerProducts;
    }

    public Dishes pricePerProducts(Set<PricePerProduct> pricePerProducts) {
        this.pricePerProducts = pricePerProducts;
        return this;
    }

    public Dishes addPricePerProduct(PricePerProduct pricePerProduct) {
        this.pricePerProducts.add(pricePerProduct);
        pricePerProduct.setProductDish(this);
        return this;
    }

    public Dishes removePricePerProduct(PricePerProduct pricePerProduct) {
        this.pricePerProducts.remove(pricePerProduct);
        pricePerProduct.setProductDish(null);
        return this;
    }

    public void setPricePerProducts(Set<PricePerProduct> pricePerProducts) {
        this.pricePerProducts = pricePerProducts;
    }

    public Set<Combos> getCombos() {
        return combos;
    }

    public Dishes combos(Set<Combos> combos) {
        this.combos = combos;
        return this;
    }

    public Dishes addCombos(Combos combos) {
        this.combos.add(combos);
        combos.getDishes().add(this);
        return this;
    }

    public Dishes removeCombos(Combos combos) {
        this.combos.remove(combos);
        combos.getDishes().remove(this);
        return this;
    }

    public void setCombos(Set<Combos> combos) {
        this.combos = combos;
    }

    public Set<Offers> getOffers() {
        return offers;
    }

    public Dishes offers(Set<Offers> offers) {
        this.offers = offers;
        return this;
    }

    public Dishes addOffer(Offers offers) {
        this.offers.add(offers);
        offers.getDishes().add(this);
        return this;
    }

    public Dishes removeOffer(Offers offers) {
        this.offers.remove(offers);
        offers.getDishes().remove(this);
        return this;
    }

    public void setOffers(Set<Offers> offers) {
        this.offers = offers;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Dishes restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public Dishes category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Orders getDishes() {
        return dishes;
    }

    public Dishes dishes(Orders orders) {
        this.dishes = orders;
        return this;
    }

    public void setDishes(Orders orders) {
        this.dishes = orders;
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
        Dishes dishes = (Dishes) o;
        if (dishes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dishes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", available='" + isAvailable() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
