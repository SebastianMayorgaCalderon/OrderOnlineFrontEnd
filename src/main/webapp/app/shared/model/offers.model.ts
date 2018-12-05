import { IDishes } from 'app/shared/model//dishes.model';
import { ICombos } from 'app/shared/model//combos.model';

export interface IOffers {
  id?: number;
  name?: string;
  price?: number;
  imageContentType?: string;
  image?: any;
  available?: boolean;
  dishes?: IDishes[];
  combos?: ICombos[];
  restaurantId?: number;
  offersName?: string;
  offersId?: number;
}

export const defaultValue: Readonly<IOffers> = {
  available: false
};
