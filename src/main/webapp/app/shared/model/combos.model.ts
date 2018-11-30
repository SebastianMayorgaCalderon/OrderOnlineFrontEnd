import { IPricePerProduct } from 'app/shared/model//price-per-product.model';
import { IOffers } from 'app/shared/model//offers.model';
import { IDishes } from 'app/shared/model//dishes.model';

export interface ICombos {
  id?: number;
  name?: string;
  available?: boolean;
  pricePerProducts?: IPricePerProduct[];
  offers?: IOffers[];
  restaurantId?: number;
  combosName?: string;
  combosId?: number;
  dishes?: IDishes[];
}

export const defaultValue: Readonly<ICombos> = {
  available: false
};
