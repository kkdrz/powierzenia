import { IEntrustment } from 'app/shared/model/entrustment.model';
import { SemesterType } from 'app/shared/model/enumerations/semester-type.model';

export interface IEntrustmentPlan {
  id?: number;
  academicYear?: number;
  semesterType?: SemesterType;
  entrustments?: IEntrustment[];
  educationPlanId?: number;
}

export const defaultValue: Readonly<IEntrustmentPlan> = {};
