import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './education-plan.reducer';

export interface IEducationPlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const EducationPlanDetail = (props: IEducationPlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {educationPlanEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          EducationPlan [<b>{educationPlanEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startAcademicYear">Start Academic Year</span>
          </dt>
          <dd>{educationPlanEntity.startAcademicYear}</dd>
          <dt>
            <span id="specialization">Specialization</span>
          </dt>
          <dd>{educationPlanEntity.specialization}</dd>
          <dt>
            <span id="studiesLevel">Studies Level</span>
          </dt>
          <dd>{educationPlanEntity.studiesLevel}</dd>
          <dt>
            <span id="studiesType">Studies Type</span>
          </dt>
          <dd>{educationPlanEntity.studiesType}</dd>
          <dt>Field Of Study</dt>
          <dd>{educationPlanEntity.fieldOfStudyId ? educationPlanEntity.fieldOfStudyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/education-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/education-plan/${educationPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({educationPlan}: IRootState) => ({
  educationPlanEntity: educationPlan.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducationPlanDetail);
