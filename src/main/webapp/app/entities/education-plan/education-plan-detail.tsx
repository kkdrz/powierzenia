import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './education-plan.reducer';
import { IEducationPlan } from 'app/shared/model/education-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEducationPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EducationPlanDetail = (props: IEducationPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { educationPlanEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.educationPlan.detail.title">EducationPlan</Translate> [<b>{educationPlanEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startAcademicYear">
              <Translate contentKey="powierzeniaApp.educationPlan.startAcademicYear">Start Academic Year</Translate>
            </span>
          </dt>
          <dd>{educationPlanEntity.startAcademicYear}</dd>
          <dt>
            <span id="specialization">
              <Translate contentKey="powierzeniaApp.educationPlan.specialization">Specialization</Translate>
            </span>
          </dt>
          <dd>{educationPlanEntity.specialization}</dd>
          <dt>
            <span id="studiesLevel">
              <Translate contentKey="powierzeniaApp.educationPlan.studiesLevel">Studies Level</Translate>
            </span>
          </dt>
          <dd>{educationPlanEntity.studiesLevel}</dd>
          <dt>
            <span id="studiesType">
              <Translate contentKey="powierzeniaApp.educationPlan.studiesType">Studies Type</Translate>
            </span>
          </dt>
          <dd>{educationPlanEntity.studiesType}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.educationPlan.fieldOfStudy">Field Of Study</Translate>
          </dt>
          <dd>{educationPlanEntity.fieldOfStudyId ? educationPlanEntity.fieldOfStudyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/education-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/education-plan/${educationPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ educationPlan }: IRootState) => ({
  educationPlanEntity: educationPlan.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducationPlanDetail);
