import './teachers-preferences.scss';

import React, {useEffect, useState} from 'react';

import {connect} from 'react-redux';
import {Col, Nav, NavItem, NavLink, Row, TabContent, TabPane} from 'reactstrap';
import classNames from 'classnames';
import {
  getEntityByUserId as getTeacherByUserId,
  updateEntity as updateTeacher
} from "app/entities/teacher/teacher.reducer";
import {getEntities as getKnowledgeAreas} from 'app/entities/knowledge-area/knowledge-area.reducer';
import {getEntities as getCourses} from 'app/entities/course/course.reducer';

import {AvailableAndChosenKnowledgeAreas} from "app/modules/teachersPreferences/available-and-chosen-knowledge-areas";
import {AvailableAndChosenPreferedCourses} from "app/modules/teachersPreferences/available-and-chosen-prefered-courses";

export interface ITeachersPreferencesProp extends StateProps, DispatchProps {
}

export const TeachersPreferences = (props: ITeachersPreferencesProp) => {
  const {account} = props;

  const [activeTab, setActiveTab] = useState('1');

  const toggle = tab => {
    if (activeTab !== tab) setActiveTab(tab);
  };

  useEffect(() => {
    props.getTeacherByUserId(account.id);
    props.getKnowledgeAreas();
    props.getCourses();
  }, []);

  const updatePreferedCourses = (preferedCourses) => {
    const teacher = {
      ...props.teacherEntity,
      preferedCourses
    };

    props.updateTeacher(teacher);
  };

  const updateKnowledgeAreas = (knowledgeAreas) => {
    const teacher = {
      ...props.teacherEntity,
      knowledgeAreas
    };

    props.updateTeacher(teacher);
  };

  const availableKnowledgeAreas = props.knowledgeAreas.filter(item => !props.teacherEntity.knowledgeAreas.some(i => i.id === item.id));
  const availablePreferedCourses = props.courses.filter(item => !props.teacherEntity.preferedCourses.some(i => i.id === item.id));


  return (
    <Row>
      <Col md="12">
        <Nav tabs>
          <NavItem>
            <NavLink
              className={classNames({active: activeTab === '1'})}
              onClick={() => {
                toggle('1');
              }}
            >
              Kompetencje
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classNames({active: activeTab === '2'})}
              onClick={() => {
                toggle('2');
              }}
            >
              Kursy
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={activeTab}>
          <TabPane tabId="1">
            <Row>
              <Col sm="12">
                <AvailableAndChosenKnowledgeAreas onUpdate={updateKnowledgeAreas}
                                                  available={availableKnowledgeAreas}
                                                  chosen={props.teacherEntity.knowledgeAreas}/>
              </Col>
            </Row>
          </TabPane>
          <TabPane tabId="2">
            <Row>
              <Col sm="12">
                <AvailableAndChosenPreferedCourses onUpdate={updatePreferedCourses}
                                                   available={availablePreferedCourses}
                                                   chosen={props.teacherEntity.preferedCourses}/>
              </Col>
            </Row>
          </TabPane>
        </TabContent>
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  knowledgeAreas: storeState.knowledgeArea.entities,
  courses: storeState.course.entities,
  teacherEntity: storeState.teacher.entity,
  isAuthenticated: storeState.authentication.isAuthenticated
});

const mapDispatchToProps = {
  getTeacherByUserId,
  getKnowledgeAreas,
  getCourses,
  updateTeacher
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeachersPreferences);
