import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IEntrustmentPlan } from 'app/shared/model/entrustment-plan.model';

export const ACTION_TYPES = {
  FETCH_ENTRUSTMENTPLAN_LIST: 'entrustmentPlan/FETCH_ENTRUSTMENTPLAN_LIST',
  FETCH_ENTRUSTMENTPLAN: 'entrustmentPlan/FETCH_ENTRUSTMENTPLAN',
  CREATE_ENTRUSTMENTPLAN: 'entrustmentPlan/CREATE_ENTRUSTMENTPLAN',
  UPDATE_ENTRUSTMENTPLAN: 'entrustmentPlan/UPDATE_ENTRUSTMENTPLAN',
  DELETE_ENTRUSTMENTPLAN: 'entrustmentPlan/DELETE_ENTRUSTMENTPLAN',
  RESET: 'entrustmentPlan/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEntrustmentPlan>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EntrustmentPlanState = Readonly<typeof initialState>;

// Reducer

export default (state: EntrustmentPlanState = initialState, action): EntrustmentPlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ENTRUSTMENTPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_ENTRUSTMENTPLAN):
    case REQUEST(ACTION_TYPES.DELETE_ENTRUSTMENTPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN):
    case FAILURE(ACTION_TYPES.CREATE_ENTRUSTMENTPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_ENTRUSTMENTPLAN):
    case FAILURE(ACTION_TYPES.DELETE_ENTRUSTMENTPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ENTRUSTMENTPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ENTRUSTMENTPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_ENTRUSTMENTPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ENTRUSTMENTPLAN):
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

const apiUrl = 'api/entrustment-plans';

// Actions

export const getEntities: ICrudGetAllAction<IEntrustmentPlan> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ENTRUSTMENTPLAN_LIST,
    payload: axios.get<IEntrustmentPlan>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEntrustmentPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ENTRUSTMENTPLAN,
    payload: axios.get<IEntrustmentPlan>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEntrustmentPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ENTRUSTMENTPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEntrustmentPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ENTRUSTMENTPLAN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEntrustmentPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ENTRUSTMENTPLAN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
