import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOffers, defaultValue } from 'app/shared/model/offers.model';

export const ACTION_TYPES = {
  FETCH_OFFERS_LIST: 'offers/FETCH_OFFERS_LIST',
  FETCH_OFFERS: 'offers/FETCH_OFFERS',
  CREATE_OFFERS: 'offers/CREATE_OFFERS',
  UPDATE_OFFERS: 'offers/UPDATE_OFFERS',
  DELETE_OFFERS: 'offers/DELETE_OFFERS',
  SET_BLOB: 'offers/SET_BLOB',
  RESET: 'offers/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOffers>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OffersState = Readonly<typeof initialState>;

// Reducer

export default (state: OffersState = initialState, action): OffersState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OFFERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OFFERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OFFERS):
    case REQUEST(ACTION_TYPES.UPDATE_OFFERS):
    case REQUEST(ACTION_TYPES.DELETE_OFFERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OFFERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OFFERS):
    case FAILURE(ACTION_TYPES.CREATE_OFFERS):
    case FAILURE(ACTION_TYPES.UPDATE_OFFERS):
    case FAILURE(ACTION_TYPES.DELETE_OFFERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OFFERS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_OFFERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OFFERS):
    case SUCCESS(ACTION_TYPES.UPDATE_OFFERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OFFERS):
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

const apiUrl = 'api/offers';

// Actions

export const getEntities: ICrudGetAllAction<IOffers> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_OFFERS_LIST,
    payload: axios.get<IOffers>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOffers> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OFFERS,
    payload: axios.get<IOffers>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOffers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OFFERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOffers> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OFFERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOffers> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OFFERS,
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
