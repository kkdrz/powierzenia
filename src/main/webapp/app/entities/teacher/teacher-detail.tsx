import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './teacher.reducer';
import { ITeacher } from 'app/shared/model/teacher.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITeacherDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TeacherDetail = (props: ITeacherDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { teacherEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.teacher.detail.title">Teacher</Translate> [<b>{teacherEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">
              <Translate contentKey="powierzeniaApp.teacher.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="powierzeniaApp.teacher.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="powierzeniaApp.teacher.email">Email</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.email}</dd>
          <dt>
            <span id="hourLimit">
              <Translate contentKey="powierzeniaApp.teacher.hourLimit">Hour Limit</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.hourLimit}</dd>
          <dt>
            <span id="pensum">
              <Translate contentKey="powierzeniaApp.teacher.pensum">Pensum</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.pensum}</dd>
          <dt>
            <span id="agreedToAdditionalPensum">
              <Translate contentKey="powierzeniaApp.teacher.agreedToAdditionalPensum">Agreed To Additional Pensum</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.agreedToAdditionalPensum ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="powierzeniaApp.teacher.type">Type</Translate>
            </span>
          </dt>
          <dd>{teacherEntity.type}</dd>
          <dt>
            <Translate contentKey="powierzeniaApp.teacher.allowedClassForms">Allowed Class Forms</Translate>
          </dt>
          <dd>
            {teacherEntity.allowedClassForms
              ? teacherEntity.allowedClassForms.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === teacherEntity.allowedClassForms.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="powierzeniaApp.teacher.knowledgeAreas">Knowledge Areas</Translate>
          </dt>
          <dd>
            {teacherEntity.knowledgeAreas
              ? teacherEntity.knowledgeAreas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === teacherEntity.knowledgeAreas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="powierzeniaApp.teacher.preferedCourses">Prefered Courses</Translate>
          </dt>
          <dd>
            {teacherEntity.preferedCourses
              ? teacherEntity.preferedCourses.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === teacherEntity.preferedCourses.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/teacher" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/teacher/${teacherEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ teacher }: IRootState) => ({
  teacherEntity: teacher.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeacherDetail);
