import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITeacher } from 'app/shared/model/teacher.model';
import { getEntities as getTeachers } from 'app/entities/teacher/teacher.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-form.reducer';
import { IClassForm } from 'app/shared/model/class-form.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassFormUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassFormUpdate = (props: IClassFormUpdateProps) => {
  const [teachersAllowedToTeachThisFormId, setTeachersAllowedToTeachThisFormId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classFormEntity, teachers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/class-form' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTeachers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...classFormEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="powierzeniaApp.classForm.home.createOrEditLabel">
            <Translate contentKey="powierzeniaApp.classForm.home.createOrEditLabel">Create or edit a ClassForm</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classFormEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-form-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="class-form-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="class-form-type">
                  <Translate contentKey="powierzeniaApp.classForm.type">Type</Translate>
                </Label>
                <AvInput
                  id="class-form-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && classFormEntity.type) || 'LABORATORY'}
                >
                  <option value="LABORATORY">{translate('powierzeniaApp.ClassFormType.LABORATORY')}</option>
                  <option value="LECTURE">{translate('powierzeniaApp.ClassFormType.LECTURE')}</option>
                  <option value="LECTURE_FIELD_OF_STUDY_SPECIFIC">
                    {translate('powierzeniaApp.ClassFormType.LECTURE_FIELD_OF_STUDY_SPECIFIC')}
                  </option>
                  <option value="PROJECT">{translate('powierzeniaApp.ClassFormType.PROJECT')}</option>
                  <option value="SEMINAR">{translate('powierzeniaApp.ClassFormType.SEMINAR')}</option>
                  <option value="EXERCISES">{translate('powierzeniaApp.ClassFormType.EXERCISES')}</option>
                  <option value="EXCERCISES_LECTORATE">{translate('powierzeniaApp.ClassFormType.EXCERCISES_LECTORATE')}</option>
                  <option value="EXERCISES_SPORT">{translate('powierzeniaApp.ClassFormType.EXERCISES_SPORT')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/class-form" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  teachers: storeState.teacher.entities,
  classFormEntity: storeState.classForm.entity,
  loading: storeState.classForm.loading,
  updating: storeState.classForm.updating,
  updateSuccess: storeState.classForm.updateSuccess
});

const mapDispatchToProps = {
  getTeachers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassFormUpdate);
