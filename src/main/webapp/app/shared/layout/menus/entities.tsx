import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/teacher">
      <Translate contentKey="global.menu.entities.teacher" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entrustment">
      <Translate contentKey="global.menu.entities.entrustment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-class">
      <Translate contentKey="global.menu.entities.courseClass" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/course">
      <Translate contentKey="global.menu.entities.course" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/education-plan">
      <Translate contentKey="global.menu.entities.educationPlan" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entrustment-plan">
      <Translate contentKey="global.menu.entities.entrustmentPlan" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/field-of-study">
      <Translate contentKey="global.menu.entities.fieldOfStudy" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/knowledge-area">
      <Translate contentKey="global.menu.entities.knowledgeArea" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-form">
      <Translate contentKey="global.menu.entities.classForm" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
