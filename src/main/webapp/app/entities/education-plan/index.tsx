import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EducationPlan from './education-plan';
import EducationPlanDetail from './education-plan-detail';
import EducationPlanUpdate from './education-plan-update';
import EducationPlanDeleteDialog from './education-plan-delete-dialog';

const Routes = ({match}) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EducationPlanDeleteDialog}/>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EducationPlanUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EducationPlanUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EducationPlanDetail}/>
      <ErrorBoundaryRoute path={match.url} component={EducationPlan}/>
    </Switch>
  </>
);

export default Routes;
