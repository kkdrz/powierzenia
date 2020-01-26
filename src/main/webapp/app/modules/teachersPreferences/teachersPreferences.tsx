import './teachersPreferences.scss';

import React, {useEffect} from 'react';

import {connect} from 'react-redux';
import {Col, Row} from 'reactstrap';
import {getEntity} from "app/entities/teacher/teacher.reducer";

export interface ITeachersPreferencesProp extends StateProps, DispatchProps {
}

export const TeachersPreferences = (props: ITeachersPreferencesProp) => {
  const {account} = props;

  useEffect(() => {
    props.getEntity(account.id);
  }, []);

  return (
    <Row>
      <Col md="9">
        <h2>Wilkommen, Parówo!</h2>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  teacherEntity: storeState.teacher.entity,
  isAuthenticated: storeState.authentication.isAuthenticated
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeachersPreferences);
