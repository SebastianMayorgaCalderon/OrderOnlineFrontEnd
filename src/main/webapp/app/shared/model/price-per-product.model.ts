import { Moment } from 'moment';

export interface IPricePerProduct {
  id?: number;
  price?: number;
  date?: Moment;
  productDishId?: number;
  productComboId?: number;
}

export const defaultValue: Readonly<IPricePerProduct> = {};
