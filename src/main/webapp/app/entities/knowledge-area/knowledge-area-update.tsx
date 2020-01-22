import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getTeachers} from 'app/entities/teacher/teacher.reducer';
import {getEntities as getCourses} from 'app/entities/course/course.reducer';
import {createEntity, getEntity, reset, updateEntity} from './knowledge-area.reducer';

export interface IKnowledgeAreaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const KnowledgeAreaUpdate = (props: IKnowledgeAreaUpdateProps) => {
  const [teachersWithThisKnowledgeAreaId, setTeachersWithThisKnowledgeAreaId] = useState('0');
  const [coursesWithThisKnowledgeAreaId, setCoursesWithThisKnowledgeAreaId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {knowledgeAreaEntity, teachers, courses, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/knowledge-area' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTeachers();
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
        ...knowledgeAreaEntity,
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
          <h2 id="powierzeniaApp.knowledgeArea.home.createOrEditLabel">Create or edit a KnowledgeArea</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : knowledgeAreaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="knowledge-area-id">ID</Label>
                  <AvInput id="knowledge-area-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="knowledge-area-type">
                  Type
                </Label>
                <AvField id="knowledge-area-type" type="text" name="type"/>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/knowledge-area" replace color="info">
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
  teachers: storeState.teacher.entities,
  courses: storeState.course.entities,
  knowledgeAreaEntity: storeState.knowledgeArea.entity,
  loading: storeState.knowledgeArea.loading,
  updating: storeState.knowledgeArea.updating,
  updateSuccess: storeState.knowledgeArea.updateSuccess
});

const mapDispatchToProps = {
  getTeachers,
  getCourses,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KnowledgeAreaUpdate);
