import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './entrustment-plan.reducer';
import { IEntrustmentPlan } from 'app/shared/model/entrustment-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntrustmentPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntrustmentPlanDetail = (props: IEntrustmentPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { entrustmentPlanEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.entrustmentPlan.detail.title">EntrustmentPlan</Translate> [<b>{entrustmentPlanEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="academicYear">
              <Translate contentKey="powierzeniaApp.entrustmentPlan.academicYear">Academic Year</Translate>
            </span>
          </dt>
          <dd>{entrustmentPlanEntity.academicYear}</dd>
          <dt>
            <span id="semesterType">
              <Translate contentKey="powierzeniaApp.entrustmentPlan.semesterType">Semester Type</Translate>
            </span>
          </dt>
          <dd>{entrustmentPlanEntity.semesterType}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.entrustmentPlan.educationPlan">Education Plan</Translate>
          </dt>
          <dd>{entrustmentPlanEntity.educationPlanId ? entrustmentPlanEntity.educationPlanId : ''}</dd>
        </dl>
        <Button tag={Link} to="/entrustment-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entrustment-plan/${entrustmentPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ entrustmentPlan }: IRootState) => ({
  entrustmentPlanEntity: entrustmentPlan.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentPlanDetail);
