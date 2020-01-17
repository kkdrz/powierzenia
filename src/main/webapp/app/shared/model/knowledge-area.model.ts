import { ITeacher } from 'app/shared/model/teacher.model';
import { ICourse } from 'app/shared/model/course.model';

export interface IKnowledgeArea {
  id?: number;
  type?: string;
  teachersWithThisKnowledgeAreas?: ITeacher[];
  coursesWithThisKnowledgeAreas?: ICourse[];
}

export const defaultValue: Readonly<IKnowledgeArea> = {};
