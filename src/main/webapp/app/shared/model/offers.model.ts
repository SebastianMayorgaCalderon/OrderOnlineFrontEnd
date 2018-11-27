import { IDishes } from 'app/shared/model//dishes.model';
import { ICombos } from 'app/shared/model//combos.model';

export interface IOffers {
  id?: number;
  name?: string;
  price?: number;
  imageContentType?: string;
  image?: any;
  restaurantId?: number;
  offersName?: string;
  offersId?: number;
  dishes?: IDishes[];
  combos?: ICombos[];
}

export const defaultValue: Readonly<IOffers> = {};
