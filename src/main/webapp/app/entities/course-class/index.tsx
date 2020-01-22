import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CourseClass from './course-class';
import CourseClassDetail from './course-class-detail';
import CourseClassUpdate from './course-class-update';
import CourseClassDeleteDialog from './course-class-delete-dialog';

const Routes = ({match}) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CourseClassDeleteDialog}/>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CourseClassUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CourseClassUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CourseClassDetail}/>
      <ErrorBoundaryRoute path={match.url} component={CourseClass}/>
    </Switch>
  </>
);

export default Routes;
