import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/category">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.category" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/dishes">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.dishes" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/offers">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.offers" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/restaurant-admin">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.restaurantAdmin" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/orders">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.orders" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/restaurant">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.restaurant" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/price-per-product">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.pricePerProduct" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/combos">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.combos" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/restaurant-admin">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.restaurantAdmin" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/restaurant">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.entities.restaurant" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
