import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './field-of-study.reducer';

export interface IFieldOfStudyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const FieldOfStudyDetail = (props: IFieldOfStudyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {fieldOfStudyEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          FieldOfStudy [<b>{fieldOfStudyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{fieldOfStudyEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/field-of-study" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-of-study/${fieldOfStudyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({fieldOfStudy}: IRootState) => ({
  fieldOfStudyEntity: fieldOfStudy.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FieldOfStudyDetail);
