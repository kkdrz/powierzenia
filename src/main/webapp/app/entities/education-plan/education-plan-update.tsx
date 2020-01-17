import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { getEntities as getFieldOfStudies } from 'app/entities/field-of-study/field-of-study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './education-plan.reducer';
import { IEducationPlan } from 'app/shared/model/education-plan.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEducationPlanUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EducationPlanUpdate = (props: IEducationPlanUpdateProps) => {
  const [fieldOfStudyId, setFieldOfStudyId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { educationPlanEntity, fieldOfStudies, loading, updating } = props;

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
          <h2 id="powierzeniaApp.educationPlan.home.createOrEditLabel">
            <Translate contentKey="powierzeniaApp.educationPlan.home.createOrEditLabel">Create or edit a EducationPlan</Translate>
          </h2>
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
                  <Label for="education-plan-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="education-plan-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startAcademicYearLabel" for="education-plan-startAcademicYear">
                  <Translate contentKey="powierzeniaApp.educationPlan.startAcademicYear">Start Academic Year</Translate>
                </Label>
                <AvField id="education-plan-startAcademicYear" type="string" className="form-control" name="startAcademicYear" />
              </AvGroup>
              <AvGroup>
                <Label id="specializationLabel" for="education-plan-specialization">
                  <Translate contentKey="powierzeniaApp.educationPlan.specialization">Specialization</Translate>
                </Label>
                <AvInput
                  id="education-plan-specialization"
                  type="select"
                  className="form-control"
                  name="specialization"
                  value={(!isNew && educationPlanEntity.specialization) || 'SOFTWARE_DEVELOPMENT'}
                >
                  <option value="SOFTWARE_DEVELOPMENT">{translate('powierzeniaApp.Specialization.SOFTWARE_DEVELOPMENT')}</option>
                  <option value="IT_SYSTEMS_DESIGN">{translate('powierzeniaApp.Specialization.IT_SYSTEMS_DESIGN')}</option>
                  <option value="DATA_SCIENCE">{translate('powierzeniaApp.Specialization.DATA_SCIENCE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="studiesLevelLabel" for="education-plan-studiesLevel">
                  <Translate contentKey="powierzeniaApp.educationPlan.studiesLevel">Studies Level</Translate>
                </Label>
                <AvInput
                  id="education-plan-studiesLevel"
                  type="select"
                  className="form-control"
                  name="studiesLevel"
                  value={(!isNew && educationPlanEntity.studiesLevel) || 'I'}
                >
                  <option value="I">{translate('powierzeniaApp.StudiesLevel.I')}</option>
                  <option value="II">{translate('powierzeniaApp.StudiesLevel.II')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="studiesTypeLabel" for="education-plan-studiesType">
                  <Translate contentKey="powierzeniaApp.educationPlan.studiesType">Studies Type</Translate>
                </Label>
                <AvInput
                  id="education-plan-studiesType"
                  type="select"
                  className="form-control"
                  name="studiesType"
                  value={(!isNew && educationPlanEntity.studiesType) || 'STATIONARY'}
                >
                  <option value="STATIONARY">{translate('powierzeniaApp.StudiesType.STATIONARY')}</option>
                  <option value="NONSTATIONARY">{translate('powierzeniaApp.StudiesType.NONSTATIONARY')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="education-plan-fieldOfStudy">
                  <Translate contentKey="powierzeniaApp.educationPlan.fieldOfStudy">Field Of Study</Translate>
                </Label>
                <AvInput id="education-plan-fieldOfStudy" type="select" className="form-control" name="fieldOfStudyId">
                  <option value="" key="0" />
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
