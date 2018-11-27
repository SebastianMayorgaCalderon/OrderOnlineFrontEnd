import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dishes from './dishes';
import DishesDetail from './dishes-detail';
import DishesUpdate from './dishes-update';
import DishesDeleteDialog from './dishes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DishesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DishesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DishesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dishes} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DishesDeleteDialog} />
  </>
);

export default Routes;
