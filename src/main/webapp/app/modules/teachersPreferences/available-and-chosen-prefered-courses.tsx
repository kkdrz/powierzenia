import './teachers-preferences.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Button, Col, Row} from 'reactstrap';


export const AvailableAndChosenPreferedCourses = (props) => {

  const leftColumnStyle = {
    borderRight: "1px solid black"
  };

  const onAvailableClick = item => {
    props.onUpdate([...props.chosen, item]);
  };

  const onChosenClick = item => {
    props.onUpdate(props.chosen.filter(i => i.id !== item.id));
  };

  return (
    <Row>
      <Col style={leftColumnStyle} sm="6">
        {props.available.map((item, i) => {
          return <Row key={i}>
            <Col xs="12" className={"mt-1"}>
              <Button onClick={() => onAvailableClick(item)} block>
                {item.name}
              </Button>
            </Col>
          </Row>
        })}
      </Col>
      <Col sm="6">
        <Row><Col><h3>Declared courses</h3></Col></Row>

        {props.chosen.map((item, i) => {
          return <Row key={i}>
            <Col xs="12" className={"mt-1"} onClick={() => onChosenClick(item)}>
              <Button color={"success"} outline block>
                {item.name}
              </Button>
            </Col>
          </Row>
        })}

      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(AvailableAndChosenPreferedCourses);
