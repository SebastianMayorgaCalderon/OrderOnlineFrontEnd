import { Moment } from 'moment';
import { IDishes } from 'app/shared/model//dishes.model';
import { ICombos } from 'app/shared/model//combos.model';
import { IOffers } from 'app/shared/model//offers.model';

export interface IOrders {
  id?: number;
  totalPrice?: number;
  subTotalPrice?: number;
  ivi?: number;
  date?: Moment;
  available?: boolean;
  dishes?: IDishes[];
  combos?: ICombos[];
  offers?: IOffers[];
  restaurantId?: number;
}

export const defaultValue: Readonly<IOrders> = {
  available: false
};
