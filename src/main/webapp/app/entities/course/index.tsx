import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Course from './course';
import CourseDetail from './course-detail';
import CourseUpdate from './course-update';
import CourseDeleteDialog from './course-delete-dialog';
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from "app/shared/auth/private-route";

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/delete`} component={CourseDeleteDialog}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/new`} component={CourseUpdate}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/edit`} component={CourseUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseDetail}/>
      <ErrorBoundaryRoute path={match.url} component={Course}/>
    </Switch>
  </>
);

export default Routes;
