import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseClass from './course-class';
import CourseClassDetail from './course-class-detail';
import CourseClassUpdate from './course-class-update';
import CourseClassDeleteDialog from './course-class-delete-dialog';
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from "app/shared/auth/private-route";

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/delete`} component={CourseClassDeleteDialog}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/new`} component={CourseClassUpdate}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER]} exact path={`${match.url}/:id/edit`} component={CourseClassUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseClassDetail}/>
      <ErrorBoundaryRoute path={match.url} component={CourseClass}/>
    </Switch>
  </>
);

export default Routes;
