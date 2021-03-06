import React from 'react';

import {NavbarBrand, NavItem, NavLink} from 'reactstrap';
import {NavLink as Link} from 'react-router-dom';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo"/>
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon/>
    <span className="brand-title">Powierzenia</span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home"/>
      <span>Home</span>
    </NavLink>
  </NavItem>
);

export const Preferences = props => (
  <NavItem>
    <NavLink tag={Link} to="/preferences" className="d-flex align-items-center">
      <FontAwesomeIcon icon="asterisk"/>
      <span>Preferences</span>
    </NavLink>
  </NavItem>
);
