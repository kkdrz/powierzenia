import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IKnowledgeArea } from 'app/shared/model/knowledge-area.model';
import { getEntities as getKnowledgeAreas } from 'app/entities/knowledge-area/knowledge-area.reducer';
import { IEducationPlan } from 'app/shared/model/education-plan.model';
import { getEntities as getEducationPlans } from 'app/entities/education-plan/education-plan.reducer';
import { ITeacher } from 'app/shared/model/teacher.model';
import { getEntities as getTeachers } from 'app/entities/teacher/teacher.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICourseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseUpdate = (props: ICourseUpdateProps) => {
  const [idstags, setIdstags] = useState([]);
  const [educationPlanId, setEducationPlanId] = useState('0');
  const [teachersThatPreferThisCourseId, setTeachersThatPreferThisCourseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { courseEntity, knowledgeAreas, educationPlans, teachers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/course' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getKnowledgeAreas();
    props.getEducationPlans();
    props.getTeachers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...courseEntity,
        ...values,
        tags: mapIdList(values.tags)
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
          <h2 id="powierzeniaApp.course.home.createOrEditLabel">
            <Translate contentKey="powierzeniaApp.course.home.createOrEditLabel">Create or edit a Course</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : courseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="course-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="course-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="course-name">
                  <Translate contentKey="powierzeniaApp.course.name">Name</Translate>
                </Label>
                <AvField id="course-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="course-code">
                  <Translate contentKey="powierzeniaApp.course.code">Code</Translate>
                </Label>
                <AvField id="course-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label for="course-tags">
                  <Translate contentKey="powierzeniaApp.course.tags">Tags</Translate>
                </Label>
                <AvInput
                  id="course-tags"
                  type="select"
                  multiple
                  className="form-control"
                  name="tags"
                  value={courseEntity.tags && courseEntity.tags.map(e => e.id)}
                >
                  <option value="" key="0" />
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
                <Label for="course-educationPlan">
                  <Translate contentKey="powierzeniaApp.course.educationPlan">Education Plan</Translate>
                </Label>
                <AvInput id="course-educationPlan" type="select" className="form-control" name="educationPlanId">
                  <option value="" key="0" />
                  {educationPlans
                    ? educationPlans.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/course" replace color="info">
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
  knowledgeAreas: storeState.knowledgeArea.entities,
  educationPlans: storeState.educationPlan.entities,
  teachers: storeState.teacher.entities,
  courseEntity: storeState.course.entity,
  loading: storeState.course.loading,
  updating: storeState.course.updating,
  updateSuccess: storeState.course.updateSuccess
});

const mapDispatchToProps = {
  getKnowledgeAreas,
  getEducationPlans,
  getTeachers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseUpdate);
