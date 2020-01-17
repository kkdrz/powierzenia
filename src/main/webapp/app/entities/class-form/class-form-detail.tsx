import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './class-form.reducer';
import { IClassForm } from 'app/shared/model/class-form.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassFormDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassFormDetail = (props: IClassFormDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classFormEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="powierzeniaApp.classForm.detail.title">ClassForm</Translate> [<b>{classFormEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">
              <Translate contentKey="powierzeniaApp.classForm.type">Type</Translate>
            </span>
          </dt>
          <dd>{classFormEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/class-form" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-form/${classFormEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classForm }: IRootState) => ({
  classFormEntity: classForm.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassFormDetail);
