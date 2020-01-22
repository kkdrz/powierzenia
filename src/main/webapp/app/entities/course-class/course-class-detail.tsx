import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './course-class.reducer';

export interface ICourseClassDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const CourseClassDetail = (props: ICourseClassDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {courseClassEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          CourseClass [<b>{courseClassEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="hours">Hours</span>
          </dt>
          <dd>{courseClassEntity.hours}</dd>
          <dt>
            <span id="form">Form</span>
          </dt>
          <dd>{courseClassEntity.form}</dd>
          <dt>Course</dt>
          <dd>{courseClassEntity.courseId ? courseClassEntity.courseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-class" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-class/${courseClassEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({courseClass}: IRootState) => ({
  courseClassEntity: courseClass.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseClassDetail);
