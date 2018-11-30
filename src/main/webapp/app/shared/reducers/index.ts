import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/category/category.reducer';
// prettier-ignore
import dishes, {
  DishesState
} from 'app/entities/dishes/dishes.reducer';
// prettier-ignore
import offers, {
  OffersState
} from 'app/entities/offers/offers.reducer';
// prettier-ignore
// prettier-ignore

// prettier-ignore

// prettier-ignore
import pricePerProduct, {
  PricePerProductState
} from 'app/entities/price-per-product/price-per-product.reducer';
// prettier-ignore
import combos, {
  CombosState
} from 'app/entities/combos/combos.reducer';
// prettier-ignore
// tslint:disable-next-line:no-duplicate-imports
import restaurantAdmin, {
  RestaurantAdminState
} from 'app/entities/restaurant-admin/restaurant-admin.reducer';
// prettier-ignore
import restaurant, {
  RestaurantState
} from 'app/entities/restaurant/restaurant.reducer';
// prettier-ignore
import orders, {
  OrdersState
} from 'app/entities/orders/orders.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly category: CategoryState;
  readonly dishes: DishesState;
  readonly offers: OffersState;
  readonly restaurantAdmin: RestaurantAdminState;
  readonly orders: OrdersState;
  readonly restaurant: RestaurantState;
  readonly pricePerProduct: PricePerProductState;
  readonly combos: CombosState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  category,
  dishes,
  offers,
  restaurantAdmin,
  orders,
  restaurant,
  pricePerProduct,
  combos,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
