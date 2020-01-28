import React from 'react';
import {Switch} from 'react-router-dom';
import Teacher from './teacher';
import Entrustment from './entrustment';
import CourseClass from './course-class';
import Course from './course';
import EducationPlan from './education-plan';
import EntrustmentPlan from './entrustment-plan';
import FieldOfStudy from './field-of-study';
import KnowledgeArea from './knowledge-area';
import ClassForm from './class-form';
import PrivateRoute from "app/shared/auth/private-route";
import {AUTHORITIES} from "app/config/constants";
import {ErrorBoundaryRoute} from 'app/shared/error/error-boundary-route';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({match}) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <PrivateRoute path={`${match.url}teacher`} hasAnyAuthorities={[AUTHORITIES.ADMIN]} component={Teacher}/>
      <PrivateRoute
        path={`${match.url}entrustment`}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER, AUTHORITIES.TEACHER]}
        component={Entrustment}
      />
      <PrivateRoute
        path={`${match.url}course-class`}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER, AUTHORITIES.TEACHER]}
        component={CourseClass}
      />
      <PrivateRoute
        path={`${match.url}course`}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER, AUTHORITIES.TEACHER]}
        component={Course}
      />
      <PrivateRoute
        path={`${match.url}education-plan`}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER, AUTHORITIES.TEACHER]}
        component={EducationPlan}
      />
      <PrivateRoute
        path={`${match.url}entrustment-plan`}
        hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.ENTRUSTER, AUTHORITIES.TEACHER]}
        component={EntrustmentPlan}
      />
      <PrivateRoute path={`${match.url}field-of-study`} hasAnyAuthorities={[AUTHORITIES.ADMIN]}
                    component={FieldOfStudy}/>
      <PrivateRoute path={`${match.url}knowledge-area`} hasAnyAuthorities={[AUTHORITIES.ADMIN]}
                    component={KnowledgeArea}/>
      <PrivateRoute path={`${match.url}class-form`} hasAnyAuthorities={[AUTHORITIES.ADMIN]} component={ClassForm}/>
      <ErrorBoundaryRoute path={`${match.url}teacher`} component={Teacher}/>
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
