import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getEntrustmentPlans} from 'app/entities/entrustment-plan/entrustment-plan.reducer';
import {getEntities as getCourseClasses} from 'app/entities/course-class/course-class.reducer';
import {getEntities as getTeachers} from 'app/entities/teacher/teacher.reducer';
import {createEntity, getEntity, reset, updateEntity} from './entrustment.reducer';

export interface IEntrustmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EntrustmentUpdate = (props: IEntrustmentUpdateProps) => {
  const [entrustmentPlanId, setEntrustmentPlanId] = useState('0');
  const [courseClassId, setCourseClassId] = useState('0');
  const [teacherId, setTeacherId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {entrustmentEntity, entrustmentPlans, courseClasses, teachers, loading, updating} = props;

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
          <h2 id="powierzeniaApp.entrustment.home.createOrEditLabel">Create or edit a Entrustment</h2>
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
                  <Label for="entrustment-id">ID</Label>
                  <AvInput id="entrustment-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="hoursLabel" for="entrustment-hours">
                  Hours
                </Label>
                <AvField id="entrustment-hours" type="string" className="form-control" name="hours"/>
              </AvGroup>
              <AvGroup>
                <Label id="hoursMultiplierLabel" for="entrustment-hoursMultiplier">
                  Hours Multiplier
                </Label>
                <AvField id="entrustment-hoursMultiplier" type="string" className="form-control"
                         name="hoursMultiplier"/>
              </AvGroup>
              <AvGroup>
                <Label for="entrustment-entrustmentPlan">Entrustment Plan</Label>
                <AvInput id="entrustment-entrustmentPlan" type="select" className="form-control"
                         name="entrustmentPlanId">
                  <option value="" key="0"/>
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
                <Label for="entrustment-courseClass">Course Class</Label>
                <AvInput id="entrustment-courseClass" type="select" className="form-control" name="courseClassId">
                  <option value="" key="0"/>
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
                <Label for="entrustment-teacher">Teacher</Label>
                <AvInput id="entrustment-teacher" type="select" className="form-control" name="teacherId">
                  <option value="" key="0"/>
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
                <FontAwesomeIcon icon="arrow-left"/>
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save"/>
                &nbsp; Save
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
