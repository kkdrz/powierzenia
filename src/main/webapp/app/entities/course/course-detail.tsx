import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './course.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICourseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseDetail = (props: ICourseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { courseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.course.detail.title">Course</Translate> [<b>{courseEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="powierzeniaApp.course.name">Name</Translate>
            </span>
          </dt>
          <dd>{courseEntity.name}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="powierzeniaApp.course.code">Code</Translate>
            </span>
          </dt>
          <dd>{courseEntity.code}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.course.tags">Tags</Translate>
          </dt>
          <dd>
            {courseEntity.tags
              ? courseEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === courseEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="powierzeniaApp.course.educationPlan">Education Plan</Translate>
          </dt>
          <dd>{courseEntity.educationPlanId ? courseEntity.educationPlanId : ''}</dd>
        </dl>
        <Button tag={Link} to="/course" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/course/${courseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ course }: IRootState) => ({
  courseEntity: course.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseDetail);
