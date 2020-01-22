import { ICourseClass } from 'app/shared/model/course-class.model';
import { IKnowledgeArea } from 'app/shared/model/knowledge-area.model';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface ICourse {
  id?: number;
  name?: string;
  code?: string;
  classes?: ICourseClass[];
  tags?: IKnowledgeArea[];
  educationPlanId?: number;
  teachersThatPreferThisCourses?: ITeacher[];
}

export const defaultValue: Readonly<ICourse> = {};
