import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EducationPlan from './education-plan';
import EducationPlanDetail from './education-plan-detail';
import EducationPlanUpdate from './education-plan-update';
import EducationPlanDeleteDialog from './education-plan-delete-dialog';
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from "app/shared/auth/private-route";

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/delete`} component={EducationPlanDeleteDialog}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/new`} component={EducationPlanUpdate}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/edit`} component={EducationPlanUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EducationPlanDetail}/>
      <ErrorBoundaryRoute path={match.url} component={EducationPlan}/>
    </Switch>
  </>
);

export default Routes;
