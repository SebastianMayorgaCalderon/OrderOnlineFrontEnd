package com.orderonline.frontend.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RestaurantAdmin.
 */
@Entity
@Table(name = "restaurant_admin")
public class RestaurantAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

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

    public RestaurantAdmin name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public RestaurantAdmin user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        RestaurantAdmin restaurantAdmin = (RestaurantAdmin) o;
        if (restaurantAdmin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantAdmin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantAdmin{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
