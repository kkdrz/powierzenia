/* tslint:disable */
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

    const index = props.chosen.index(item);
    if (index !== -1) {
      props.onUpdate(...props.chosen.split(index, 1));
    }

  };

  return (
    <Row>
      <Col style={leftColumnStyle} sm="6">
        {props.available.map((item, i) => {
          return <Row className={"mt-2"} key={i}>
            <Col sm={12}>
              <Button onClick={() => onAvailableClick(item)} block>
                {item.type}
              </Button>
            </Col>
          </Row>
        })}
      </Col>
      <Col sm="6">
        <Row><Col><h3>Declared competences</h3></Col></Row>
        {props.chosen.map((item, i) => {
          return <Row className={"mt-2"} key={i}>
            <Col sm={12} onClick={() => onChosenClick(item)}>
              <Button block>
                {item.type}
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

export default connect(mapStateToProps, mapDispatchToProps)(AvailableAndChosenKnowledgeAreas);
