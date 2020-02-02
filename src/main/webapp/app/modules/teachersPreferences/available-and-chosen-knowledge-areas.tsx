import './teachers-preferences.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Button, Col, Row} from 'reactstrap';


export const AvailableAndChosenKnowledgeAreas = (props) => {

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
        <Row>
          {props.available.map((item, i) => {
            return <Col key={i} xs="auto" className={"mt-1 pr-1 "}>
              <Button onClick={() => onAvailableClick(item)}>
                {item.type}
              </Button>
            </Col>
          })}
        </Row>
      </Col>
      <Col sm="6">
        <Row><Col><h3>Declared competences</h3></Col></Row>
        <Row>
          {props.chosen.map((item, i) => {
            return <Col key={i} xs="auto" className={"mt-1 pr-1 "} onClick={() => onChosenClick(item)}>
              <Button color={"success"} outline block>
                {item.type}
              </Button>
            </Col>
          })}
        </Row>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(AvailableAndChosenKnowledgeAreas);
