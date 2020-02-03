import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import {NavDropdown} from './menu-components';

export interface IEntitiesMenuProps {
  isAdmin: boolean;
  isEntruster: boolean;
  isTeacher: boolean;
}

export const EntitiesMenu = (props: IEntitiesMenuProps) => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    {props.isAdmin || props.isEntruster ? <MenuItem icon="asterisk" to="/teacher">
      Teachers
    </MenuItem> : null}
    {props.isAdmin || props.isEntruster || props.isTeacher ? <MenuItem icon="asterisk" to="/entrustment">
      Entrustments
    </MenuItem> : null}
    {props.isAdmin || props.isEntruster ? <MenuItem icon="asterisk" to="/course-class">
      Course Classes
    </MenuItem> : null}
    {props.isAdmin || props.isEntruster ? <MenuItem icon="asterisk" to="/course">
      Courses
    </MenuItem> : null}
    {props.isAdmin || props.isEntruster ? <MenuItem icon="asterisk" to="/education-plan">
      Education Plans
    </MenuItem> : null}
    {props.isAdmin || props.isEntruster ? <MenuItem icon="asterisk" to="/entrustment-plan">
      Entrustment Plans
    </MenuItem> : null}
    {props.isAdmin ? <MenuItem icon="asterisk" to="/field-of-study">
      Fields Of Study
    </MenuItem> : null}
    {props.isAdmin ? <MenuItem icon="asterisk" to="/knowledge-area">
      Knowledge Areas
    </MenuItem> : null}
    {props.isAdmin ? <MenuItem icon="asterisk" to="/class-form">
      Class Forms
    </MenuItem> : null}
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);


