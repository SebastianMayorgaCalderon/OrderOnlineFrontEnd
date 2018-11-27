import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PricePerProduct from './price-per-product';
import PricePerProductDetail from './price-per-product-detail';
import PricePerProductUpdate from './price-per-product-update';
import PricePerProductDeleteDialog from './price-per-product-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PricePerProductUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PricePerProductUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PricePerProductDetail} />
      <ErrorBoundaryRoute path={match.url} component={PricePerProduct} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PricePerProductDeleteDialog} />
  </>
);

export default Routes;
