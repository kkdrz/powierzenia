import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './entrustment-plan.reducer';

export interface IEntrustmentPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EntrustmentPlanDetail = (props: IEntrustmentPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {entrustmentPlanEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          EntrustmentPlan [<b>{entrustmentPlanEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="academicYear">Academic Year</span>
          </dt>
          <dd>{entrustmentPlanEntity.academicYear}</dd>
          <dt>
            <span id="semesterType">Semester Type</span>
          </dt>
          <dd>{entrustmentPlanEntity.semesterType}</dd>
          <dt>Education Plan</dt>
          <dd>{entrustmentPlanEntity.educationPlanId ? entrustmentPlanEntity.educationPlanId : ''}</dd>
        </dl>
        <Button tag={Link} to="/entrustment-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entrustment-plan/${entrustmentPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({entrustmentPlan}: IRootState) => ({
  entrustmentPlanEntity: entrustmentPlan.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentPlanDetail);
