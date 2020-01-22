import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KnowledgeArea from './knowledge-area';
import KnowledgeAreaDetail from './knowledge-area-detail';
import KnowledgeAreaUpdate from './knowledge-area-update';
import KnowledgeAreaDeleteDialog from './knowledge-area-delete-dialog';

const Routes = ({match}) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KnowledgeAreaDeleteDialog}/>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KnowledgeAreaUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KnowledgeAreaUpdate}/>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KnowledgeAreaDetail}/>
      <ErrorBoundaryRoute path={match.url} component={KnowledgeArea}/>
    </Switch>
  </>
);

export default Routes;
