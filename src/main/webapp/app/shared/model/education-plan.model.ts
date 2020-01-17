import { IEntrustmentPlan } from 'app/shared/model/entrustment-plan.model';
import { ICourse } from 'app/shared/model/course.model';
import { Specialization } from 'app/shared/model/enumerations/specialization.model';
import { StudiesLevel } from 'app/shared/model/enumerations/studies-level.model';
import { StudiesType } from 'app/shared/model/enumerations/studies-type.model';

export interface IEducationPlan {
  id?: number;
  startAcademicYear?: number;
  specialization?: Specialization;
  studiesLevel?: StudiesLevel;
  studiesType?: StudiesType;
  entrustmentPlans?: IEntrustmentPlan[];
  courses?: ICourse[];
  fieldOfStudyId?: number;
}

export const defaultValue: Readonly<IEducationPlan> = {};
