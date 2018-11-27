import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Offers from './offers';
import OffersDetail from './offers-detail';
import OffersUpdate from './offers-update';
import OffersDeleteDialog from './offers-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OffersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OffersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OffersDetail} />
      <ErrorBoundaryRoute path={match.url} component={Offers} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OffersDeleteDialog} />
  </>
);

export default Routes;
