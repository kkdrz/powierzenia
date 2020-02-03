import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KnowledgeArea from './knowledge-area';
import KnowledgeAreaDetail from './knowledge-area-detail';
import KnowledgeAreaUpdate from './knowledge-area-update';
import KnowledgeAreaDeleteDialog from './knowledge-area-delete-dialog';
import PrivateRoute from 'app/shared/auth/private-route';
import {AUTHORITIES} from 'app/config/constants';

const Routes = ({match}) => (
  <>
    <Switch>
      <PrivateRoute exact path={`${match.url}/:id/delete`} hasAnyAuthorities={[AUTHORITIES.ADMIN]}
                    component={KnowledgeAreaDeleteDialog}/>
      <PrivateRoute exact path={`${match.url}/new`} hasAnyAuthorities={[AUTHORITIES.ADMIN]}
                    component={KnowledgeAreaUpdate}/>
      <PrivateRoute exact path={`${match.url}/:id/edit`} hasAnyAuthorities={[AUTHORITIES.ADMIN]}
                    component={KnowledgeAreaUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KnowledgeAreaDetail}/>
      <ErrorBoundaryRoute path={match.url} component={KnowledgeArea}/>
    </Switch>
  </>
);

export default Routes;
