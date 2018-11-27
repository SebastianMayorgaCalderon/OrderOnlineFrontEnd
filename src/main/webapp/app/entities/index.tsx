import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Category from './category';
import Dishes from './dishes';
import Offers from './offers';
import RestaurantAdmin from './restaurant-admin';
import Orders from './orders';
import Restaurant from './restaurant';
import PricePerProduct from './price-per-product';
import Combos from './combos';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}/dishes`} component={Dishes} />
      <ErrorBoundaryRoute path={`${match.url}/offers`} component={Offers} />
      <ErrorBoundaryRoute path={`${match.url}/restaurant-admin`} component={RestaurantAdmin} />
      <ErrorBoundaryRoute path={`${match.url}/orders`} component={Orders} />
      <ErrorBoundaryRoute path={`${match.url}/restaurant`} component={Restaurant} />
      <ErrorBoundaryRoute path={`${match.url}/price-per-product`} component={PricePerProduct} />
      <ErrorBoundaryRoute path={`${match.url}/combos`} component={Combos} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
