import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDishes, defaultValue } from 'app/shared/model/dishes.model';

export const ACTION_TYPES = {
  FETCH_DISHES_LIST: 'dishes/FETCH_DISHES_LIST',
  FETCH_DISHES: 'dishes/FETCH_DISHES',
  CREATE_DISHES: 'dishes/CREATE_DISHES',
  UPDATE_DISHES: 'dishes/UPDATE_DISHES',
  DELETE_DISHES: 'dishes/DELETE_DISHES',
  SET_BLOB: 'dishes/SET_BLOB',
  RESET: 'dishes/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDishes>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DishesState = Readonly<typeof initialState>;

// Reducer

export default (state: DishesState = initialState, action): DishesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISHES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISHES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DISHES):
    case REQUEST(ACTION_TYPES.UPDATE_DISHES):
    case REQUEST(ACTION_TYPES.DELETE_DISHES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DISHES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISHES):
    case FAILURE(ACTION_TYPES.CREATE_DISHES):
    case FAILURE(ACTION_TYPES.UPDATE_DISHES):
    case FAILURE(ACTION_TYPES.DELETE_DISHES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISHES_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISHES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISHES):
    case SUCCESS(ACTION_TYPES.UPDATE_DISHES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISHES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/dishes';

// Actions

export const getEntities: ICrudGetAllAction<IDishes> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DISHES_LIST,
    payload: axios.get<IDishes>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDishes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISHES,
    payload: axios.get<IDishes>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDishes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISHES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDishes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISHES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDishes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISHES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
