import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './course-class.reducer';
import { ICourseClass } from 'app/shared/model/course-class.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICourseClassDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseClassDetail = (props: ICourseClassDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { courseClassEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.courseClass.detail.title">CourseClass</Translate> [<b>{courseClassEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="hours">
              <Translate contentKey="powierzeniaApp.courseClass.hours">Hours</Translate>
            </span>
          </dt>
          <dd>{courseClassEntity.hours}</dd>
          <dt>
            <span id="form">
              <Translate contentKey="powierzeniaApp.courseClass.form">Form</Translate>
            </span>
          </dt>
          <dd>{courseClassEntity.form}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.courseClass.course">Course</Translate>
          </dt>
          <dd>{courseClassEntity.courseId ? courseClassEntity.courseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/course-class" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course-class/${courseClassEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ courseClass }: IRootState) => ({
  courseClassEntity: courseClass.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseClassDetail);
