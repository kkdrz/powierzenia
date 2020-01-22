import React from 'react';
import {Switch} from 'react-router-dom';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Teacher from './teacher';
import Entrustment from './entrustment';
import CourseClass from './course-class';
import Course from './course';
import EducationPlan from './education-plan';
import EntrustmentPlan from './entrustment-plan';
import FieldOfStudy from './field-of-study';
import KnowledgeArea from './knowledge-area';
import ClassForm from './class-form';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({match}) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}teacher`} component={Teacher}/>
      <ErrorBoundaryRoute path={`${match.url}entrustment`} component={Entrustment}/>
      <ErrorBoundaryRoute path={`${match.url}course-class`} component={CourseClass}/>
      <ErrorBoundaryRoute path={`${match.url}course`} component={Course}/>
      <ErrorBoundaryRoute path={`${match.url}education-plan`} component={EducationPlan}/>
      <ErrorBoundaryRoute path={`${match.url}entrustment-plan`} component={EntrustmentPlan}/>
      <ErrorBoundaryRoute path={`${match.url}field-of-study`} component={FieldOfStudy}/>
      <ErrorBoundaryRoute path={`${match.url}knowledge-area`} component={KnowledgeArea}/>
      <ErrorBoundaryRoute path={`${match.url}class-form`} component={ClassForm}/>
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
