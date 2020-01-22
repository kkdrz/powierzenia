import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getTeachers} from 'app/entities/teacher/teacher.reducer';
import {createEntity, getEntity, reset, updateEntity} from './class-form.reducer';

export interface IClassFormUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ClassFormUpdate = (props: IClassFormUpdateProps) => {
  const [teachersAllowedToTeachThisFormId, setTeachersAllowedToTeachThisFormId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {classFormEntity, teachers, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/class-form' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...classFormEntity,
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
          <h2 id="powierzeniaApp.classForm.home.createOrEditLabel">Create or edit a ClassForm</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classFormEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-form-id">ID</Label>
                  <AvInput id="class-form-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="class-form-type">
                  Type
                </Label>
                <AvInput
                  id="class-form-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && classFormEntity.type) || 'LABORATORY'}
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
              <Button tag={Link} id="cancel-save" to="/class-form" replace color="info">
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
  classFormEntity: storeState.classForm.entity,
  loading: storeState.classForm.loading,
  updating: storeState.classForm.updating,
  updateSuccess: storeState.classForm.updateSuccess
});

const mapDispatchToProps = {
  getTeachers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassFormUpdate);
