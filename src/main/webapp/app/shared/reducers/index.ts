import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import teacher, {
  TeacherState
} from 'app/entities/teacher/teacher.reducer';
// prettier-ignore
import entrustment, {
  EntrustmentState
} from 'app/entities/entrustment/entrustment.reducer';
// prettier-ignore
import courseClass, {
  CourseClassState
} from 'app/entities/course-class/course-class.reducer';
// prettier-ignore
import course, {
  CourseState
} from 'app/entities/course/course.reducer';
// prettier-ignore
import educationPlan, {
  EducationPlanState
} from 'app/entities/education-plan/education-plan.reducer';
// prettier-ignore
import entrustmentPlan, {
  EntrustmentPlanState
} from 'app/entities/entrustment-plan/entrustment-plan.reducer';
// prettier-ignore
import fieldOfStudy, {
  FieldOfStudyState
} from 'app/entities/field-of-study/field-of-study.reducer';
// prettier-ignore
import knowledgeArea, {
  KnowledgeAreaState
} from 'app/entities/knowledge-area/knowledge-area.reducer';
// prettier-ignore
import classForm, {
  ClassFormState
} from 'app/entities/class-form/class-form.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly teacher: TeacherState;
  readonly entrustment: EntrustmentState;
  readonly courseClass: CourseClassState;
  readonly course: CourseState;
  readonly educationPlan: EducationPlanState;
  readonly entrustmentPlan: EntrustmentPlanState;
  readonly fieldOfStudy: FieldOfStudyState;
  readonly knowledgeArea: KnowledgeAreaState;
  readonly classForm: ClassFormState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  teacher,
  entrustment,
  courseClass,
  course,
  educationPlan,
  entrustmentPlan,
  fieldOfStudy,
  knowledgeArea,
  classForm,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
