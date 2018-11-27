import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Combos from './combos';
import CombosDetail from './combos-detail';
import CombosUpdate from './combos-update';
import CombosDeleteDialog from './combos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CombosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CombosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CombosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Combos} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CombosDeleteDialog} />
  </>
);

export default Routes;
