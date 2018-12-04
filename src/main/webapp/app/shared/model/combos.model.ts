import { IPricePerProduct } from 'app/shared/model//price-per-product.model';
import { IDishes } from 'app/shared/model//dishes.model';
import { IOffers } from 'app/shared/model//offers.model';

export interface ICombos {
  id?: number;
  name?: string;
  available?: boolean;
  pricePerProducts?: IPricePerProduct[];
  dishes?: IDishes[];
  restaurantId?: number;
  combosName?: string;
  combosId?: number;
  offers?: IOffers[];
}

export const defaultValue: Readonly<ICombos> = {
  available: false
};
