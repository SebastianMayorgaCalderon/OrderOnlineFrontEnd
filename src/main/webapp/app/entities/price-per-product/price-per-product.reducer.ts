import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPricePerProduct, defaultValue } from 'app/shared/model/price-per-product.model';

export const ACTION_TYPES = {
  FETCH_PRICEPERPRODUCT_LIST: 'pricePerProduct/FETCH_PRICEPERPRODUCT_LIST',
  FETCH_PRICEPERPRODUCT: 'pricePerProduct/FETCH_PRICEPERPRODUCT',
  CREATE_PRICEPERPRODUCT: 'pricePerProduct/CREATE_PRICEPERPRODUCT',
  UPDATE_PRICEPERPRODUCT: 'pricePerProduct/UPDATE_PRICEPERPRODUCT',
  DELETE_PRICEPERPRODUCT: 'pricePerProduct/DELETE_PRICEPERPRODUCT',
  RESET: 'pricePerProduct/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPricePerProduct>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PricePerProductState = Readonly<typeof initialState>;

// Reducer

export default (state: PricePerProductState = initialState, action): PricePerProductState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRICEPERPRODUCT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRICEPERPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRICEPERPRODUCT):
    case REQUEST(ACTION_TYPES.UPDATE_PRICEPERPRODUCT):
    case REQUEST(ACTION_TYPES.DELETE_PRICEPERPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRICEPERPRODUCT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRICEPERPRODUCT):
    case FAILURE(ACTION_TYPES.CREATE_PRICEPERPRODUCT):
    case FAILURE(ACTION_TYPES.UPDATE_PRICEPERPRODUCT):
    case FAILURE(ACTION_TYPES.DELETE_PRICEPERPRODUCT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICEPERPRODUCT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRICEPERPRODUCT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRICEPERPRODUCT):
    case SUCCESS(ACTION_TYPES.UPDATE_PRICEPERPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRICEPERPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/price-per-products';

// Actions

export const getEntities: ICrudGetAllAction<IPricePerProduct> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRICEPERPRODUCT_LIST,
    payload: axios.get<IPricePerProduct>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPricePerProduct> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRICEPERPRODUCT,
    payload: axios.get<IPricePerProduct>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPricePerProduct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRICEPERPRODUCT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPricePerProduct> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRICEPERPRODUCT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPricePerProduct> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRICEPERPRODUCT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
