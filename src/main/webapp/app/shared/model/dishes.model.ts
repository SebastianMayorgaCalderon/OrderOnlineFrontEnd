import { IPricePerProduct } from 'app/shared/model//price-per-product.model';
import { ICombos } from 'app/shared/model//combos.model';
import { IOffers } from 'app/shared/model//offers.model';

export interface IDishes {
  id?: number;
  name?: string;
  description?: string;
  available?: boolean;
  imageContentType?: string;
  image?: any;
  pricePerProducts?: IPricePerProduct[];
  combos?: ICombos[];
  offers?: IOffers[];
  restaurantId?: number;
  categoryName?: string;
  categoryId?: number;
  dishesName?: string;
  dishesId?: number;
}

export const defaultValue: Readonly<IDishes> = {
  available: false
};
