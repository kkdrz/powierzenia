import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Table} from 'reactstrap';
import {getSortState, JhiItemCount, JhiPagination} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntities} from './education-plan.reducer';
import {ITEMS_PER_PAGE} from 'app/shared/util/pagination.constants';

export interface IEducationPlanProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export const EducationPlan = (props: IEducationPlanProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  useEffect(() => {
    getAllEntities();
  }, []);

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });

  const {educationPlanList, match, totalItems} = props;
  return (
    <div>
      <h2 id="education-plan-heading">
        Education Plans
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus"/>
          &nbsp; Create new Education Plan
        </Link>
      </h2>
      <div className="table-responsive">
        {educationPlanList && educationPlanList.length > 0 ? (
          <Table responsive>
            <thead>
            <tr>
              <th className="hand" onClick={sort('id')}>
                ID <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('startAcademicYear')}>
                Start Academic Year <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('specialization')}>
                Specialization <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('studiesLevel')}>
                Studies Level <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('studiesType')}>
                Studies Type <FontAwesomeIcon icon="sort"/>
              </th>
              <th>
                Field Of Study <FontAwesomeIcon icon="sort"/>
              </th>
              <th/>
            </tr>
            </thead>
            <tbody>
            {educationPlanList.map((educationPlan, i) => (
              <tr key={`entity-${i}`}>
                <td>
                  <Button tag={Link} to={`${match.url}/${educationPlan.id}`} color="link" size="sm">
                    {educationPlan.id}
                  </Button>
                </td>
                <td>{educationPlan.startAcademicYear}</td>
                <td>{educationPlan.specialization}</td>
                <td>{educationPlan.studiesLevel}</td>
                <td>{educationPlan.studiesType}</td>
                <td>
                  {educationPlan.fieldOfStudyId ? (
                    <Link to={`field-of-study/${educationPlan.fieldOfStudyId}`}>{educationPlan.fieldOfStudyId}</Link>
                  ) : (
                    ''
                  )}
                </td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`${match.url}/${educationPlan.id}`} color="info" size="sm">
                      <FontAwesomeIcon icon="eye"/> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${educationPlan.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                    >
                      <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${educationPlan.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="danger"
                      size="sm"
                    >
                      <FontAwesomeIcon icon="trash"/> <span className="d-none d-md-inline">Delete</span>
                    </Button>
                  </div>
                </td>
              </tr>
            ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">No Education Plans found</div>
        )}
      </div>
      <div className={educationPlanList && educationPlanList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems}
                        itemsPerPage={paginationState.itemsPerPage}/>
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({educationPlan}: IRootState) => ({
  educationPlanList: educationPlan.entities,
  totalItems: educationPlan.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducationPlan);
