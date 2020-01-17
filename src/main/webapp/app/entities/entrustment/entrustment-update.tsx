import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEntrustmentPlan } from 'app/shared/model/entrustment-plan.model';
import { getEntities as getEntrustmentPlans } from 'app/entities/entrustment-plan/entrustment-plan.reducer';
import { ICourseClass } from 'app/shared/model/course-class.model';
import { getEntities as getCourseClasses } from 'app/entities/course-class/course-class.reducer';
import { ITeacher } from 'app/shared/model/teacher.model';
import { getEntities as getTeachers } from 'app/entities/teacher/teacher.reducer';
import { getEntity, updateEntity, createEntity, reset } from './entrustment.reducer';
import { IEntrustment } from 'app/shared/model/entrustment.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEntrustmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntrustmentUpdate = (props: IEntrustmentUpdateProps) => {
  const [entrustmentPlanId, setEntrustmentPlanId] = useState('0');
  const [courseClassId, setCourseClassId] = useState('0');
  const [teacherId, setTeacherId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { entrustmentEntity, entrustmentPlans, courseClasses, teachers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/entrustment' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEntrustmentPlans();
    props.getCourseClasses();
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
        ...entrustmentEntity,
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
          <h2 id="powierzeniaApp.entrustment.home.createOrEditLabel">
            <Translate contentKey="powierzeniaApp.entrustment.home.createOrEditLabel">Create or edit a Entrustment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : entrustmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="entrustment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="entrustment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="hoursLabel" for="entrustment-hours">
                  <Translate contentKey="powierzeniaApp.entrustment.hours">Hours</Translate>
                </Label>
                <AvField id="entrustment-hours" type="string" className="form-control" name="hours" />
              </AvGroup>
              <AvGroup>
                <Label id="hoursMultiplierLabel" for="entrustment-hoursMultiplier">
                  <Translate contentKey="powierzeniaApp.entrustment.hoursMultiplier">Hours Multiplier</Translate>
                </Label>
                <AvField id="entrustment-hoursMultiplier" type="string" className="form-control" name="hoursMultiplier" />
              </AvGroup>
              <AvGroup>
                <Label for="entrustment-entrustmentPlan">
                  <Translate contentKey="powierzeniaApp.entrustment.entrustmentPlan">Entrustment Plan</Translate>
                </Label>
                <AvInput id="entrustment-entrustmentPlan" type="select" className="form-control" name="entrustmentPlanId">
                  <option value="" key="0" />
                  {entrustmentPlans
                    ? entrustmentPlans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="entrustment-courseClass">
                  <Translate contentKey="powierzeniaApp.entrustment.courseClass">Course Class</Translate>
                </Label>
                <AvInput id="entrustment-courseClass" type="select" className="form-control" name="courseClassId">
                  <option value="" key="0" />
                  {courseClasses
                    ? courseClasses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="entrustment-teacher">
                  <Translate contentKey="powierzeniaApp.entrustment.teacher">Teacher</Translate>
                </Label>
                <AvInput id="entrustment-teacher" type="select" className="form-control" name="teacherId">
                  <option value="" key="0" />
                  {teachers
                    ? teachers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/entrustment" replace color="info">
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
  entrustmentPlans: storeState.entrustmentPlan.entities,
  courseClasses: storeState.courseClass.entities,
  teachers: storeState.teacher.entities,
  entrustmentEntity: storeState.entrustment.entity,
  loading: storeState.entrustment.loading,
  updating: storeState.entrustment.updating,
  updateSuccess: storeState.entrustment.updateSuccess
});

const mapDispatchToProps = {
  getEntrustmentPlans,
  getCourseClasses,
  getTeachers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentUpdate);
