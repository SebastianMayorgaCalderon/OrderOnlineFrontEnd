import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICombos, defaultValue } from 'app/shared/model/combos.model';

export const ACTION_TYPES = {
  FETCH_COMBOS_LIST: 'combos/FETCH_COMBOS_LIST',
  FETCH_COMBOS: 'combos/FETCH_COMBOS',
  CREATE_COMBOS: 'combos/CREATE_COMBOS',
  UPDATE_COMBOS: 'combos/UPDATE_COMBOS',
  DELETE_COMBOS: 'combos/DELETE_COMBOS',
  RESET: 'combos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICombos>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CombosState = Readonly<typeof initialState>;

// Reducer

export default (state: CombosState = initialState, action): CombosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMBOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMBOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COMBOS):
    case REQUEST(ACTION_TYPES.UPDATE_COMBOS):
    case REQUEST(ACTION_TYPES.DELETE_COMBOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COMBOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMBOS):
    case FAILURE(ACTION_TYPES.CREATE_COMBOS):
    case FAILURE(ACTION_TYPES.UPDATE_COMBOS):
    case FAILURE(ACTION_TYPES.DELETE_COMBOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMBOS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMBOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMBOS):
    case SUCCESS(ACTION_TYPES.UPDATE_COMBOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMBOS):
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

const apiUrl = 'api/combos';

// Actions

export const getEntities: ICrudGetAllAction<ICombos> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMBOS_LIST,
    payload: axios.get<ICombos>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICombos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMBOS,
    payload: axios.get<ICombos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICombos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMBOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICombos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMBOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICombos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMBOS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
