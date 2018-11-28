import { ICategory } from 'app/shared/model//category.model';
import { IDishes } from 'app/shared/model//dishes.model';
import { IOrders } from 'app/shared/model//orders.model';
import { ICombos } from 'app/shared/model//combos.model';
import { IOffers } from 'app/shared/model//offers.model';

export interface IRestaurant {
  id?: number;
  name?: string;
  userID?: number;
  restaurantAdminName?: string;
  restaurantAdminId?: number;
  categories?: ICategory[];
  dishes?: IDishes[];
  orders?: IOrders[];
  combos?: ICombos[];
  offers?: IOffers[];
}

export const defaultValue: Readonly<IRestaurant> = {};
