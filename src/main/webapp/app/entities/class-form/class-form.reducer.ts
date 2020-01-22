import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IClassForm } from 'app/shared/model/class-form.model';

export const ACTION_TYPES = {
  FETCH_CLASSFORM_LIST: 'classForm/FETCH_CLASSFORM_LIST',
  FETCH_CLASSFORM: 'classForm/FETCH_CLASSFORM',
  CREATE_CLASSFORM: 'classForm/CREATE_CLASSFORM',
  UPDATE_CLASSFORM: 'classForm/UPDATE_CLASSFORM',
  DELETE_CLASSFORM: 'classForm/DELETE_CLASSFORM',
  RESET: 'classForm/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClassForm>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClassFormState = Readonly<typeof initialState>;

// Reducer

export default (state: ClassFormState = initialState, action): ClassFormState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLASSFORM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLASSFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLASSFORM):
    case REQUEST(ACTION_TYPES.UPDATE_CLASSFORM):
    case REQUEST(ACTION_TYPES.DELETE_CLASSFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLASSFORM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLASSFORM):
    case FAILURE(ACTION_TYPES.CREATE_CLASSFORM):
    case FAILURE(ACTION_TYPES.UPDATE_CLASSFORM):
    case FAILURE(ACTION_TYPES.DELETE_CLASSFORM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSFORM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSFORM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLASSFORM):
    case SUCCESS(ACTION_TYPES.UPDATE_CLASSFORM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLASSFORM):
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

const apiUrl = 'api/class-forms';

// Actions

export const getEntities: ICrudGetAllAction<IClassForm> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLASSFORM_LIST,
    payload: axios.get<IClassForm>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClassForm> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLASSFORM,
    payload: axios.get<IClassForm>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClassForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLASSFORM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClassForm> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLASSFORM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClassForm> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLASSFORM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
