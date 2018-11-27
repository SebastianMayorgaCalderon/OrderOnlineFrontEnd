import { IDishes } from 'app/shared/model//dishes.model';

export interface ICategory {
  id?: number;
  name?: string;
  dishes?: IDishes[];
  restaurantId?: number;
}

export const defaultValue: Readonly<ICategory> = {};
