import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getClassForms} from 'app/entities/class-form/class-form.reducer';
import {getEntities as getKnowledgeAreas} from 'app/entities/knowledge-area/knowledge-area.reducer';
import {getEntities as getCourses} from 'app/entities/course/course.reducer';
import {createEntity, getEntity, reset, updateEntity} from './teacher.reducer';
import {mapIdList} from 'app/shared/util/entity-utils';

export interface ITeacherUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const TeacherUpdate = (props: ITeacherUpdateProps) => {
  const [idsallowedClassForms, setIdsallowedClassForms] = useState([]);
  const [idsknowledgeAreas, setIdsknowledgeAreas] = useState([]);
  const [idspreferedCourses, setIdspreferedCourses] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {teacherEntity, classForms, knowledgeAreas, courses, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/teacher' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClassForms();
    props.getKnowledgeAreas();
    props.getCourses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...teacherEntity,
        ...values,
        allowedClassForms: mapIdList(values.allowedClassForms),
        knowledgeAreas: mapIdList(values.knowledgeAreas),
        preferedCourses: mapIdList(values.preferedCourses)
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="powierzeniaApp.teacher.home.createOrEditLabel">Create or edit a Teacher</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : teacherEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="teacher-id">ID</Label>
                  <AvInput id="teacher-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="externalUserIdLabel" for="teacher-externalUserId">
                  External User Id
                </Label>
                <AvField id="teacher-externalUserId" type="text" name="externalUserId"/>
              </AvGroup>
              <AvGroup>
                <Label id="firstNameLabel" for="teacher-firstName">
                  First Name
                </Label>
                <AvField id="teacher-firstName" type="text" name="firstName"/>
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="teacher-lastName">
                  Last Name
                </Label>
                <AvField id="teacher-lastName" type="text" name="lastName"/>
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="teacher-email">
                  Email
                </Label>
                <AvField id="teacher-email" type="text" name="email"/>
              </AvGroup>
              <AvGroup>
                <Label id="hourLimitLabel" for="teacher-hourLimit">
                  Hour Limit
                </Label>
                <AvField id="teacher-hourLimit" type="string" className="form-control" name="hourLimit"/>
              </AvGroup>
              <AvGroup>
                <Label id="pensumLabel" for="teacher-pensum">
                  Pensum
                </Label>
                <AvField id="teacher-pensum" type="string" className="form-control" name="pensum"/>
              </AvGroup>
              <AvGroup check>
                <Label id="agreedToAdditionalPensumLabel">
                  <AvInput
                    id="teacher-agreedToAdditionalPensum"
                    type="checkbox"
                    className="form-check-input"
                    name="agreedToAdditionalPensum"
                  />
                  Agreed To Additional Pensum
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="teacher-type">
                  Type
                </Label>
                <AvInput
                  id="teacher-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && teacherEntity.type) || 'EXTERNAL_SPECIALIST'}
                >
                  <option value="EXTERNAL_SPECIALIST">EXTERNAL_SPECIALIST</option>
                  <option value="DOCTORATE_STUDENT">DOCTORATE_STUDENT</option>
                  <option value="ACADEMIC_TEACHER">ACADEMIC_TEACHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="teacher-allowedClassForms">Allowed Class Forms</Label>
                <AvInput
                  id="teacher-allowedClassForms"
                  type="select"
                  multiple
                  className="form-control"
                  name="allowedClassForms"
                  value={teacherEntity.allowedClassForms && teacherEntity.allowedClassForms.map(e => e.id)}
                >
                  <option value="" key="0"/>
                  {classForms
                    ? classForms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="teacher-knowledgeAreas">Knowledge Areas</Label>
                <AvInput
                  id="teacher-knowledgeAreas"
                  type="select"
                  multiple
                  className="form-control"
                  name="knowledgeAreas"
                  value={teacherEntity.knowledgeAreas && teacherEntity.knowledgeAreas.map(e => e.id)}
                >
                  <option value="" key="0"/>
                  {knowledgeAreas
                    ? knowledgeAreas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="teacher-preferedCourses">Prefered Courses</Label>
                <AvInput
                  id="teacher-preferedCourses"
                  type="select"
                  multiple
                  className="form-control"
                  name="preferedCourses"
                  value={teacherEntity.preferedCourses && teacherEntity.preferedCourses.map(e => e.id)}
                >
                  <option value="" key="0"/>
                  {courses
                    ? courses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/teacher" replace color="info">
                <FontAwesomeIcon icon="arrow-left"/>
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save"/>
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  classForms: storeState.classForm.entities,
  knowledgeAreas: storeState.knowledgeArea.entities,
  courses: storeState.course.entities,
  teacherEntity: storeState.teacher.entity,
  loading: storeState.teacher.loading,
  updating: storeState.teacher.updating,
  updateSuccess: storeState.teacher.updateSuccess
});

const mapDispatchToProps = {
  getClassForms,
  getKnowledgeAreas,
  getCourses,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeacherUpdate);
