import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './entrustment.reducer';
import { IEntrustment } from 'app/shared/model/entrustment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntrustmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntrustmentDetail = (props: IEntrustmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { entrustmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.entrustment.detail.title">Entrustment</Translate> [<b>{entrustmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="hours">
              <Translate contentKey="powierzeniaApp.entrustment.hours">Hours</Translate>
            </span>
          </dt>
          <dd>{entrustmentEntity.hours}</dd>
          <dt>
            <span id="hoursMultiplier">
              <Translate contentKey="powierzeniaApp.entrustment.hoursMultiplier">Hours Multiplier</Translate>
            </span>
          </dt>
          <dd>{entrustmentEntity.hoursMultiplier}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.entrustment.entrustmentPlan">Entrustment Plan</Translate>
          </dt>
          <dd>{entrustmentEntity.entrustmentPlanId ? entrustmentEntity.entrustmentPlanId : ''}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.entrustment.courseClass">Course Class</Translate>
          </dt>
          <dd>{entrustmentEntity.courseClassId ? entrustmentEntity.courseClassId : ''}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.entrustment.teacher">Teacher</Translate>
          </dt>
          <dd>{entrustmentEntity.teacherId ? entrustmentEntity.teacherId : ''}</dd>
        </dl>
        <Button tag={Link} to="/entrustment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entrustment/${entrustmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ entrustment }: IRootState) => ({
  entrustmentEntity: entrustment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntrustmentDetail);
