import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import {NavDropdown} from './menu-components';

export const EntitiesMenu = () => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/teacher">
      Teacher
    </MenuItem>
    <MenuItem icon="asterisk" to="/entrustment">
      Entrustment
    </MenuItem>
    <MenuItem icon="asterisk" to="/course-class">
      Course Class
    </MenuItem>
    <MenuItem icon="asterisk" to="/course">
      Course
    </MenuItem>
    <MenuItem icon="asterisk" to="/education-plan">
      Education Plan
    </MenuItem>
    <MenuItem icon="asterisk" to="/entrustment-plan">
      Entrustment Plan
    </MenuItem>
    <MenuItem icon="asterisk" to="/field-of-study">
      Field Of Study
    </MenuItem>
    <MenuItem icon="asterisk" to="/knowledge-area">
      Knowledge Area
    </MenuItem>
    <MenuItem icon="asterisk" to="/class-form">
      Class Form
    </MenuItem>
    <MenuItem icon="asterisk" to="/teacher">
      Teacher
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
