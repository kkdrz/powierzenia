export interface IEntrustment {
  id?: number;
  hours?: number;
  hoursMultiplier?: number;
  entrustmentPlanId?: number;
  courseClassId?: number;
  teacherId?: number;
}

export const defaultValue: Readonly<IEntrustment> = {};
