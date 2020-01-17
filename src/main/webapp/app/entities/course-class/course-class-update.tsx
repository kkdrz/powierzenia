import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-class.reducer';
import { ICourseClass } from 'app/shared/model/course-class.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICourseClassUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseClassUpdate = (props: ICourseClassUpdateProps) => {
  const [courseId, setCourseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { courseClassEntity, courses, loading, updating } = props;

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
          <h2 id="powierzeniaApp.courseClass.home.createOrEditLabel">
            <Translate contentKey="powierzeniaApp.courseClass.home.createOrEditLabel">Create or edit a CourseClass</Translate>
          </h2>
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
                  <Label for="course-class-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="course-class-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="hoursLabel" for="course-class-hours">
                  <Translate contentKey="powierzeniaApp.courseClass.hours">Hours</Translate>
                </Label>
                <AvField id="course-class-hours" type="string" className="form-control" name="hours" />
              </AvGroup>
              <AvGroup>
                <Label id="formLabel" for="course-class-form">
                  <Translate contentKey="powierzeniaApp.courseClass.form">Form</Translate>
                </Label>
                <AvInput
                  id="course-class-form"
                  type="select"
                  className="form-control"
                  name="form"
                  value={(!isNew && courseClassEntity.form) || 'LABORATORY'}
                >
                  <option value="LABORATORY">{translate('powierzeniaApp.ClassFormType.LABORATORY')}</option>
                  <option value="LECTURE">{translate('powierzeniaApp.ClassFormType.LECTURE')}</option>
                  <option value="LECTURE_FIELD_OF_STUDY_SPECIFIC">
                    {translate('powierzeniaApp.ClassFormType.LECTURE_FIELD_OF_STUDY_SPECIFIC')}
                  </option>
                  <option value="PROJECT">{translate('powierzeniaApp.ClassFormType.PROJECT')}</option>
                  <option value="SEMINAR">{translate('powierzeniaApp.ClassFormType.SEMINAR')}</option>
                  <option value="EXERCISES">{translate('powierzeniaApp.ClassFormType.EXERCISES')}</option>
                  <option value="EXCERCISES_LECTORATE">{translate('powierzeniaApp.ClassFormType.EXCERCISES_LECTORATE')}</option>
                  <option value="EXERCISES_SPORT">{translate('powierzeniaApp.ClassFormType.EXERCISES_SPORT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="course-class-course">
                  <Translate contentKey="powierzeniaApp.courseClass.course">Course</Translate>
                </Label>
                <AvInput id="course-class-course" type="select" className="form-control" name="courseId">
                  <option value="" key="0" />
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
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
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
