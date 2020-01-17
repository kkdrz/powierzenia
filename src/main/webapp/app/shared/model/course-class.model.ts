import { IEntrustment } from 'app/shared/model/entrustment.model';
import { ClassFormType } from 'app/shared/model/enumerations/class-form-type.model';

export interface ICourseClass {
  id?: number;
  hours?: number;
  form?: ClassFormType;
  entrustments?: IEntrustment[];
  courseId?: number;
}

export const defaultValue: Readonly<ICourseClass> = {};
