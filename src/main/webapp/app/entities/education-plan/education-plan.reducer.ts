import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEducationPlan, defaultValue } from 'app/shared/model/education-plan.model';

export const ACTION_TYPES = {
  FETCH_EDUCATIONPLAN_LIST: 'educationPlan/FETCH_EDUCATIONPLAN_LIST',
  FETCH_EDUCATIONPLAN: 'educationPlan/FETCH_EDUCATIONPLAN',
  CREATE_EDUCATIONPLAN: 'educationPlan/CREATE_EDUCATIONPLAN',
  UPDATE_EDUCATIONPLAN: 'educationPlan/UPDATE_EDUCATIONPLAN',
  DELETE_EDUCATIONPLAN: 'educationPlan/DELETE_EDUCATIONPLAN',
  RESET: 'educationPlan/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEducationPlan>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EducationPlanState = Readonly<typeof initialState>;

// Reducer

export default (state: EducationPlanState = initialState, action): EducationPlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EDUCATIONPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EDUCATIONPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EDUCATIONPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_EDUCATIONPLAN):
    case REQUEST(ACTION_TYPES.DELETE_EDUCATIONPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EDUCATIONPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EDUCATIONPLAN):
    case FAILURE(ACTION_TYPES.CREATE_EDUCATIONPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_EDUCATIONPLAN):
    case FAILURE(ACTION_TYPES.DELETE_EDUCATIONPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDUCATIONPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDUCATIONPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EDUCATIONPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_EDUCATIONPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EDUCATIONPLAN):
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

const apiUrl = 'api/education-plans';

// Actions

export const getEntities: ICrudGetAllAction<IEducationPlan> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EDUCATIONPLAN_LIST,
    payload: axios.get<IEducationPlan>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEducationPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EDUCATIONPLAN,
    payload: axios.get<IEducationPlan>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEducationPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EDUCATIONPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEducationPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EDUCATIONPLAN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEducationPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EDUCATIONPLAN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
