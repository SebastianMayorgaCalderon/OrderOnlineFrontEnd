import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRestaurantAdmin, defaultValue } from 'app/shared/model/restaurant-admin.model';

export const ACTION_TYPES = {
  FETCH_RESTAURANTADMIN_LIST: 'restaurantAdmin/FETCH_RESTAURANTADMIN_LIST',
  FETCH_RESTAURANTADMIN: 'restaurantAdmin/FETCH_RESTAURANTADMIN',
  CREATE_RESTAURANTADMIN: 'restaurantAdmin/CREATE_RESTAURANTADMIN',
  UPDATE_RESTAURANTADMIN: 'restaurantAdmin/UPDATE_RESTAURANTADMIN',
  DELETE_RESTAURANTADMIN: 'restaurantAdmin/DELETE_RESTAURANTADMIN',
  RESET: 'restaurantAdmin/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRestaurantAdmin>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RestaurantAdminState = Readonly<typeof initialState>;

// Reducer

export default (state: RestaurantAdminState = initialState, action): RestaurantAdminState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESTAURANTADMIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESTAURANTADMIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RESTAURANTADMIN):
    case REQUEST(ACTION_TYPES.UPDATE_RESTAURANTADMIN):
    case REQUEST(ACTION_TYPES.DELETE_RESTAURANTADMIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RESTAURANTADMIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESTAURANTADMIN):
    case FAILURE(ACTION_TYPES.CREATE_RESTAURANTADMIN):
    case FAILURE(ACTION_TYPES.UPDATE_RESTAURANTADMIN):
    case FAILURE(ACTION_TYPES.DELETE_RESTAURANTADMIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESTAURANTADMIN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESTAURANTADMIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESTAURANTADMIN):
    case SUCCESS(ACTION_TYPES.UPDATE_RESTAURANTADMIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESTAURANTADMIN):
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

const apiUrl = 'api/restaurant-admins';

// Actions

export const getEntities: ICrudGetAllAction<IRestaurantAdmin> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RESTAURANTADMIN_LIST,
    payload: axios.get<IRestaurantAdmin>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRestaurantAdmin> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESTAURANTADMIN,
    payload: axios.get<IRestaurantAdmin>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRestaurantAdmin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESTAURANTADMIN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRestaurantAdmin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESTAURANTADMIN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRestaurantAdmin> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESTAURANTADMIN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
