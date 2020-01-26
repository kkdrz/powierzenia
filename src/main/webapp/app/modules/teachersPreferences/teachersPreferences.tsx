import './teachersPreferences.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Col, Row} from 'reactstrap';

export type ITeachersPreferencesProp = StateProps;

export const TeachersPreferences = (props: ITeachersPreferencesProp) => {
  const {account} = props;

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
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(TeachersPreferences);
