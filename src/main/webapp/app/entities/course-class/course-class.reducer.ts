import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, ICourseClass } from 'app/shared/model/course-class.model';

export const ACTION_TYPES = {
  FETCH_COURSECLASS_LIST: 'courseClass/FETCH_COURSECLASS_LIST',
  FETCH_COURSECLASS: 'courseClass/FETCH_COURSECLASS',
  CREATE_COURSECLASS: 'courseClass/CREATE_COURSECLASS',
  UPDATE_COURSECLASS: 'courseClass/UPDATE_COURSECLASS',
  DELETE_COURSECLASS: 'courseClass/DELETE_COURSECLASS',
  RESET: 'courseClass/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICourseClass>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CourseClassState = Readonly<typeof initialState>;

// Reducer

export default (state: CourseClassState = initialState, action): CourseClassState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COURSECLASS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COURSECLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COURSECLASS):
    case REQUEST(ACTION_TYPES.UPDATE_COURSECLASS):
    case REQUEST(ACTION_TYPES.DELETE_COURSECLASS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COURSECLASS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COURSECLASS):
    case FAILURE(ACTION_TYPES.CREATE_COURSECLASS):
    case FAILURE(ACTION_TYPES.UPDATE_COURSECLASS):
    case FAILURE(ACTION_TYPES.DELETE_COURSECLASS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSECLASS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSECLASS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COURSECLASS):
    case SUCCESS(ACTION_TYPES.UPDATE_COURSECLASS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COURSECLASS):
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

const apiUrl = 'api/course-classes';

// Actions

export const getEntities: ICrudGetAllAction<ICourseClass> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COURSECLASS_LIST,
    payload: axios.get<ICourseClass>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICourseClass> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COURSECLASS,
    payload: axios.get<ICourseClass>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICourseClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COURSECLASS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICourseClass> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COURSECLASS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICourseClass> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COURSECLASS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
