import './home.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Alert, Col, Row} from 'reactstrap';
import {getLoginUrl} from 'app/shared/util/url-utils';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const {account} = props;

  return (
    <Row>
      <Col md="9">
        <h2>Welcome!</h2>
        <p className="lead">This is your homepage</p>
        {account && account.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <a href={getLoginUrl()} className="alert-link">
                <span> sign in</span>
              </a>
              , you can try the default accounts:
              <br/>- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
              <br/>- User (login=&quot;user&quot; and password=&quot;user&quot;).
              <br/>- Teacher (login=&quot;teacher&quot; and password=&quot;teacher&quot;).
              <br/>- Entruster (login=&quot;entruster&quot; and password=&quot;entruster&quot;).
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
