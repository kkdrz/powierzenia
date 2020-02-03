import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Teacher from './teacher';
import TeacherDetail from './teacher-detail';
import TeacherUpdate from './teacher-update';
import TeacherDeleteDialog from './teacher-delete-dialog';
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from 'app/shared/auth/private-route';

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]} exact path={`${match.url}/:id/delete`}
                    component={TeacherDeleteDialog}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]} exact path={`${match.url}/new`} component={TeacherUpdate}/>
      <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]} exact path={`${match.url}/:id/edit`}
                    component={TeacherUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TeacherDetail}/>
      <ErrorBoundaryRoute path={match.url} component={Teacher}/>
    </Switch>
  </>
);

export default Routes;
