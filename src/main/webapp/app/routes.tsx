import React from 'react';
import {Switch} from 'react-router-dom';
import Loadable from 'react-loadable';

import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import {AUTHORITIES} from 'app/config/constants';
import TeachersPreferences from "app/modules/teachersPreferences/teachers-preferences";

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>
});

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <PrivateRoute path="/preferences" component={TeachersPreferences} hasAnyAuthorities={[AUTHORITIES.TEACHER]}/>
      <ErrorBoundaryRoute path="/logout" component={Logout}/>
      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]}/>
      <ErrorBoundaryRoute path="/" exact component={Home}/>
      <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]}/>
      <ErrorBoundaryRoute component={PageNotFound}/>
    </Switch>
  </div>
);

export default Routes;
