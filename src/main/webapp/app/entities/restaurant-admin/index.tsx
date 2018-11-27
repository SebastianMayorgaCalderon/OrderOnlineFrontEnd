import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RestaurantAdmin from './restaurant-admin';
import RestaurantAdminDetail from './restaurant-admin-detail';
import RestaurantAdminUpdate from './restaurant-admin-update';
import RestaurantAdminDeleteDialog from './restaurant-admin-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RestaurantAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RestaurantAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RestaurantAdminDetail} />
      <ErrorBoundaryRoute path={match.url} component={RestaurantAdmin} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RestaurantAdminDeleteDialog} />
  </>
);

export default Routes;
