import { IEntrustment } from 'app/shared/model/entrustment.model';
import { IClassForm } from 'app/shared/model/class-form.model';
import { IKnowledgeArea } from 'app/shared/model/knowledge-area.model';
import { ICourse } from 'app/shared/model/course.model';
import { TeacherType } from 'app/shared/model/enumerations/teacher-type.model';

export interface ITeacher {
  id?: number;
  externalUserId?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  hourLimit?: number;
  pensum?: number;
  agreedToAdditionalPensum?: boolean;
  type?: TeacherType;
  entrustments?: IEntrustment[];
  allowedClassForms?: IClassForm[];
  knowledgeAreas?: IKnowledgeArea[];
  preferedCourses?: ICourse[];
}

export const defaultValue: Readonly<ITeacher> = {
  agreedToAdditionalPensum: false
};
