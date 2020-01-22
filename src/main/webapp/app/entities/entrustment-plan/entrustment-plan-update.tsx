import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getEducationPlans} from 'app/entities/education-plan/education-plan.reducer';
import {createEntity, getEntity, reset, updateEntity} from './entrustment-plan.reducer';

export interface IEntrustmentPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EntrustmentPlanUpdate = (props: IEntrustmentPlanUpdateProps) => {
  const [educationPlanId, setEducationPlanId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {entrustmentPlanEntity, educationPlans, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/entrustment-plan' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEducationPlans();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...entrustmentPlanEntity,
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
          <h2 id="powierzeniaApp.entrustmentPlan.home.createOrEditLabel">Create or edit a EntrustmentPlan</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : entrustmentPlanEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="entrustment-plan-id">ID</Label>
                  <AvInput id="entrustment-plan-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="academicYearLabel" for="entrustment-plan-academicYear">
                  Academic Year
                </Label>
                <AvField id="entrustment-plan-academicYear" type="string" className="form-control" name="academicYear"/>
              </AvGroup>
              <AvGroup>
                <Label id="semesterTypeLabel" for="entrustment-plan-semesterType">
                  Semester Type
                </Label>
                <AvInput
                  id="entrustment-plan-semesterType"
                  type="select"
                  className="form-control"
                  name="semesterType"
                  value={(!isNew && entrustmentPlanEntity.semesterType) || 'SUMMER'}
                >
                  <option value="SUMMER">SUMMER</option>
                  <option value="WINTER">WINTER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="entrustment-plan-educationPlan">Education Plan</Label>
                <AvInput id="entrustment-plan-educationPlan" type="select" className="form-control"
                         name="educationPlanId">
                  <option value="" key="0"/>
                  {educationPlans
                    ? educationPlans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/entrustment-plan" replace color="info">
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
  educationPlans: storeState.educationPlan.entities,
  entrustmentPlanEntity: storeState.entrustmentPlan.entity,
  loading: storeState.entrustmentPlan.loading,
  updating: storeState.entrustmentPlan.updating,
  updateSuccess: storeState.entrustmentPlan.updateSuccess
});

const mapDispatchToProps = {
  getEducationPlans,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentPlanUpdate);
