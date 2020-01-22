import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './knowledge-area.reducer';

export interface IKnowledgeAreaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const KnowledgeAreaDetail = (props: IKnowledgeAreaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {knowledgeAreaEntity} = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          KnowledgeArea [<b>{knowledgeAreaEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{knowledgeAreaEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/knowledge-area" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/knowledge-area/${knowledgeAreaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({knowledgeArea}: IRootState) => ({
  knowledgeAreaEntity: knowledgeArea.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KnowledgeAreaDetail);
