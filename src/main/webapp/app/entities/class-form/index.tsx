import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClassForm from './class-form';
import ClassFormDetail from './class-form-detail';
import ClassFormUpdate from './class-form-update';
import ClassFormDeleteDialog from './class-form-delete-dialog';

const Routes = ({match}) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClassFormDeleteDialog}/>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClassFormUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClassFormUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClassFormDetail}/>
      <ErrorBoundaryRoute path={match.url} component={ClassForm}/>
    </Switch>
  </>
);

export default Routes;
