import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Entrustment from './entrustment';
import EntrustmentDetail from './entrustment-detail';
import EntrustmentUpdate from './entrustment-update';
import EntrustmentDeleteDialog from './entrustment-delete-dialog';
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from "app/shared/auth/private-route";

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/delete`} component={EntrustmentDeleteDialog}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/new`} component={EntrustmentUpdate}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/edit`} component={EntrustmentUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntrustmentDetail}/>
      <ErrorBoundaryRoute path={match.url} component={Entrustment}/>
    </Switch>
  </>
);

export default Routes;
