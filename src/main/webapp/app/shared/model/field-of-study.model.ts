import { IEducationPlan } from 'app/shared/model/education-plan.model';

export interface IFieldOfStudy {
  id?: number;
  name?: string;
  educationPlans?: IEducationPlan[];
}

export const defaultValue: Readonly<IFieldOfStudy> = {};
