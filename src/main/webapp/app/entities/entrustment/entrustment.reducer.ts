import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IEntrustment } from 'app/shared/model/entrustment.model';

export const ACTION_TYPES = {
  FETCH_ENTRUSTMENT_LIST: 'entrustment/FETCH_ENTRUSTMENT_LIST',
  FETCH_ENTRUSTMENT: 'entrustment/FETCH_ENTRUSTMENT',
  CREATE_ENTRUSTMENT: 'entrustment/CREATE_ENTRUSTMENT',
  UPDATE_ENTRUSTMENT: 'entrustment/UPDATE_ENTRUSTMENT',
  DELETE_ENTRUSTMENT: 'entrustment/DELETE_ENTRUSTMENT',
  RESET: 'entrustment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEntrustment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EntrustmentState = Readonly<typeof initialState>;

// Reducer

export default (state: EntrustmentState = initialState, action): EntrustmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENTRUSTMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENTRUSTMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ENTRUSTMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ENTRUSTMENT):
    case REQUEST(ACTION_TYPES.DELETE_ENTRUSTMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ENTRUSTMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENTRUSTMENT):
    case FAILURE(ACTION_TYPES.CREATE_ENTRUSTMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ENTRUSTMENT):
    case FAILURE(ACTION_TYPES.DELETE_ENTRUSTMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENTRUSTMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENTRUSTMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENTRUSTMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ENTRUSTMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENTRUSTMENT):
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

const apiUrl = 'api/entrustments';

// Actions

export const getEntities: ICrudGetAllAction<IEntrustment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ENTRUSTMENT_LIST,
    payload: axios.get<IEntrustment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEntrustment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENTRUSTMENT,
    payload: axios.get<IEntrustment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEntrustment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENTRUSTMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEntrustment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENTRUSTMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEntrustment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENTRUSTMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
