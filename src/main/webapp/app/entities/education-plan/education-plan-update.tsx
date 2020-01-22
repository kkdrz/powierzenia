import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getFieldOfStudies} from 'app/entities/field-of-study/field-of-study.reducer';
import {createEntity, getEntity, reset, updateEntity} from './education-plan.reducer';

export interface IEducationPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EducationPlanUpdate = (props: IEducationPlanUpdateProps) => {
  const [fieldOfStudyId, setFieldOfStudyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {educationPlanEntity, fieldOfStudies, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/education-plan' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFieldOfStudies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...educationPlanEntity,
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
          <h2 id="powierzeniaApp.educationPlan.home.createOrEditLabel">Create or edit a EducationPlan</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : educationPlanEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="education-plan-id">ID</Label>
                  <AvInput id="education-plan-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startAcademicYearLabel" for="education-plan-startAcademicYear">
                  Start Academic Year
                </Label>
                <AvField id="education-plan-startAcademicYear" type="string" className="form-control"
                         name="startAcademicYear"/>
              </AvGroup>
              <AvGroup>
                <Label id="specializationLabel" for="education-plan-specialization">
                  Specialization
                </Label>
                <AvInput
                  id="education-plan-specialization"
                  type="select"
                  className="form-control"
                  name="specialization"
                  value={(!isNew && educationPlanEntity.specialization) || 'SOFTWARE_DEVELOPMENT'}
                >
                  <option value="SOFTWARE_DEVELOPMENT">SOFTWARE_DEVELOPMENT</option>
                  <option value="IT_SYSTEMS_DESIGN">IT_SYSTEMS_DESIGN</option>
                  <option value="DATA_SCIENCE">DATA_SCIENCE</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="studiesLevelLabel" for="education-plan-studiesLevel">
                  Studies Level
                </Label>
                <AvInput
                  id="education-plan-studiesLevel"
                  type="select"
                  className="form-control"
                  name="studiesLevel"
                  value={(!isNew && educationPlanEntity.studiesLevel) || 'I'}
                >
                  <option value="I">I</option>
                  <option value="II">II</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="studiesTypeLabel" for="education-plan-studiesType">
                  Studies Type
                </Label>
                <AvInput
                  id="education-plan-studiesType"
                  type="select"
                  className="form-control"
                  name="studiesType"
                  value={(!isNew && educationPlanEntity.studiesType) || 'STATIONARY'}
                >
                  <option value="STATIONARY">STATIONARY</option>
                  <option value="NONSTATIONARY">NONSTATIONARY</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="education-plan-fieldOfStudy">Field Of Study</Label>
                <AvInput id="education-plan-fieldOfStudy" type="select" className="form-control" name="fieldOfStudyId">
                  <option value="" key="0"/>
                  {fieldOfStudies
                    ? fieldOfStudies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/education-plan" replace color="info">
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
  fieldOfStudies: storeState.fieldOfStudy.entities,
  educationPlanEntity: storeState.educationPlan.entity,
  loading: storeState.educationPlan.loading,
  updating: storeState.educationPlan.updating,
  updateSuccess: storeState.educationPlan.updateSuccess
});

const mapDispatchToProps = {
  getFieldOfStudies,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducationPlanUpdate);
