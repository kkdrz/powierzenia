import './teachers-preferences.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Col, Row} from 'reactstrap';


export const AvailableAndChosenPreferedCourses = (props) => {

  return (
    <Row>
      <Col sm="6">
        {props.available.map((item, i) => {
          return <span key={i}>{item.type}</span>
        })}
      </Col>
      <Col sm="6">
        {props.chosen.map((item, i) => {
          return {item}
        })}
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(AvailableAndChosenPreferedCourses);
