import { ITeacher } from 'app/shared/model/teacher.model';
import { ClassFormType } from 'app/shared/model/enumerations/class-form-type.model';

export interface IClassForm {
  id?: number;
  type?: ClassFormType;
  teachersAllowedToTeachThisForms?: ITeacher[];
}

export const defaultValue: Readonly<IClassForm> = {};
