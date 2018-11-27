package com.orderonline.frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PricePerProduct.
 */
@Entity
@Table(name = "price_per_product")
public class PricePerProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @ManyToOne
    @JsonIgnoreProperties("pricePerProducts")
    private Dishes productDish;

    @ManyToOne
    @JsonIgnoreProperties("pricePerProducts")
    private Combos productCombo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public PricePerProduct price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getDate() {
        return date;
    }

    public PricePerProduct date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Dishes getProductDish() {
        return productDish;
    }

    public PricePerProduct productDish(Dishes dishes) {
        this.productDish = dishes;
        return this;
    }

    public void setProductDish(Dishes dishes) {
        this.productDish = dishes;
    }

    public Combos getProductCombo() {
        return productCombo;
    }

    public PricePerProduct productCombo(Combos combos) {
        this.productCombo = combos;
        return this;
    }

    public void setProductCombo(Combos combos) {
        this.productCombo = combos;
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
        PricePerProduct pricePerProduct = (PricePerProduct) o;
        if (pricePerProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pricePerProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PricePerProduct{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
