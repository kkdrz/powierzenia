import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './teacher.reducer';

export interface ITeacherDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const TeacherDetail = (props: ITeacherDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {teacherEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Teacher [<b>{teacherEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="externalUserId">External User Id</span>
          </dt>
          <dd>{teacherEntity.externalUserId}</dd>
          <dt>
            <span id="firstName">First Name</span>
          </dt>
          <dd>{teacherEntity.firstName}</dd>
          <dt>
            <span id="lastName">Last Name</span>
          </dt>
          <dd>{teacherEntity.lastName}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{teacherEntity.email}</dd>
          <dt>
            <span id="hourLimit">Hour Limit</span>
          </dt>
          <dd>{teacherEntity.hourLimit}</dd>
          <dt>
            <span id="pensum">Pensum</span>
          </dt>
          <dd>{teacherEntity.pensum}</dd>
          <dt>
            <span id="agreedToAdditionalPensum">Agreed To Additional Pensum</span>
          </dt>
          <dd>{teacherEntity.agreedToAdditionalPensum ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{teacherEntity.type}</dd>
          <dt>Allowed Class Forms</dt>
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
          <dt>Knowledge Areas</dt>
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
          <dt>Prefered Courses</dt>
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
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/teacher/${teacherEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({teacher}: IRootState) => ({
  teacherEntity: teacher.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeacherDetail);
