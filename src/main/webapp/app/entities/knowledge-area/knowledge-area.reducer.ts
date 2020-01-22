import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IKnowledgeArea } from 'app/shared/model/knowledge-area.model';

export const ACTION_TYPES = {
  FETCH_KNOWLEDGEAREA_LIST: 'knowledgeArea/FETCH_KNOWLEDGEAREA_LIST',
  FETCH_KNOWLEDGEAREA: 'knowledgeArea/FETCH_KNOWLEDGEAREA',
  CREATE_KNOWLEDGEAREA: 'knowledgeArea/CREATE_KNOWLEDGEAREA',
  UPDATE_KNOWLEDGEAREA: 'knowledgeArea/UPDATE_KNOWLEDGEAREA',
  DELETE_KNOWLEDGEAREA: 'knowledgeArea/DELETE_KNOWLEDGEAREA',
  RESET: 'knowledgeArea/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKnowledgeArea>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type KnowledgeAreaState = Readonly<typeof initialState>;

// Reducer

export default (state: KnowledgeAreaState = initialState, action): KnowledgeAreaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KNOWLEDGEAREA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KNOWLEDGEAREA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_KNOWLEDGEAREA):
    case REQUEST(ACTION_TYPES.UPDATE_KNOWLEDGEAREA):
    case REQUEST(ACTION_TYPES.DELETE_KNOWLEDGEAREA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_KNOWLEDGEAREA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KNOWLEDGEAREA):
    case FAILURE(ACTION_TYPES.CREATE_KNOWLEDGEAREA):
    case FAILURE(ACTION_TYPES.UPDATE_KNOWLEDGEAREA):
    case FAILURE(ACTION_TYPES.DELETE_KNOWLEDGEAREA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_KNOWLEDGEAREA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_KNOWLEDGEAREA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_KNOWLEDGEAREA):
    case SUCCESS(ACTION_TYPES.UPDATE_KNOWLEDGEAREA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_KNOWLEDGEAREA):
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

const apiUrl = 'api/knowledge-areas';

// Actions

export const getEntities: ICrudGetAllAction<IKnowledgeArea> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KNOWLEDGEAREA_LIST,
    payload: axios.get<IKnowledgeArea>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IKnowledgeArea> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KNOWLEDGEAREA,
    payload: axios.get<IKnowledgeArea>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IKnowledgeArea> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KNOWLEDGEAREA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IKnowledgeArea> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KNOWLEDGEAREA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKnowledgeArea> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KNOWLEDGEAREA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
