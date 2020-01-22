import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getCourses} from 'app/entities/course/course.reducer';
import {createEntity, getEntity, reset, updateEntity} from './course-class.reducer';

export interface ICourseClassUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const CourseClassUpdate = (props: ICourseClassUpdateProps) => {
  const [courseId, setCourseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {courseClassEntity, courses, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/course-class' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...courseClassEntity,
        ...values
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
          <h2 id="powierzeniaApp.courseClass.home.createOrEditLabel">Create or edit a CourseClass</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : courseClassEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="course-class-id">ID</Label>
                  <AvInput id="course-class-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="hoursLabel" for="course-class-hours">
                  Hours
                </Label>
                <AvField id="course-class-hours" type="string" className="form-control" name="hours"/>
              </AvGroup>
              <AvGroup>
                <Label id="formLabel" for="course-class-form">
                  Form
                </Label>
                <AvInput
                  id="course-class-form"
                  type="select"
                  className="form-control"
                  name="form"
                  value={(!isNew && courseClassEntity.form) || 'LABORATORY'}
                >
                  <option value="LABORATORY">LABORATORY</option>
                  <option value="LECTURE">LECTURE</option>
                  <option value="LECTURE_FIELD_OF_STUDY_SPECIFIC">LECTURE_FIELD_OF_STUDY_SPECIFIC</option>
                  <option value="PROJECT">PROJECT</option>
                  <option value="SEMINAR">SEMINAR</option>
                  <option value="EXERCISES">EXERCISES</option>
                  <option value="EXCERCISES_LECTORATE">EXCERCISES_LECTORATE</option>
                  <option value="EXERCISES_SPORT">EXERCISES_SPORT</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="course-class-course">Course</Label>
                <AvInput id="course-class-course" type="select" className="form-control" name="courseId">
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
              <Button tag={Link} id="cancel-save" to="/course-class" replace color="info">
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
  courses: storeState.course.entities,
  courseClassEntity: storeState.courseClass.entity,
  loading: storeState.courseClass.loading,
  updating: storeState.courseClass.updating,
  updateSuccess: storeState.courseClass.updateSuccess
});

const mapDispatchToProps = {
  getCourses,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseClassUpdate);
