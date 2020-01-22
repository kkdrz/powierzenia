import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './entrustment.reducer';

export interface IEntrustmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EntrustmentDetail = (props: IEntrustmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {entrustmentEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Entrustment [<b>{entrustmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="hours">Hours</span>
          </dt>
          <dd>{entrustmentEntity.hours}</dd>
          <dt>
            <span id="hoursMultiplier">Hours Multiplier</span>
          </dt>
          <dd>{entrustmentEntity.hoursMultiplier}</dd>
          <dt>Entrustment Plan</dt>
          <dd>{entrustmentEntity.entrustmentPlanId ? entrustmentEntity.entrustmentPlanId : ''}</dd>
          <dt>Course Class</dt>
          <dd>{entrustmentEntity.courseClassId ? entrustmentEntity.courseClassId : ''}</dd>
          <dt>Teacher</dt>
          <dd>{entrustmentEntity.teacherId ? entrustmentEntity.teacherId : ''}</dd>
        </dl>
        <Button tag={Link} to="/entrustment" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entrustment/${entrustmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({entrustment}: IRootState) => ({
  entrustmentEntity: entrustment.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentDetail);
