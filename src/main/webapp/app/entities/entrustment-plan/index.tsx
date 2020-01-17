import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EntrustmentPlan from './entrustment-plan';
import EntrustmentPlanDetail from './entrustment-plan-detail';
import EntrustmentPlanUpdate from './entrustment-plan-update';
import EntrustmentPlanDeleteDialog from './entrustment-plan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EntrustmentPlanDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EntrustmentPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EntrustmentPlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntrustmentPlanDetail} />
      <ErrorBoundaryRoute path={match.url} component={EntrustmentPlan} />
    </Switch>
  </>
);

export default Routes;
