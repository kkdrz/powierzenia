import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Table} from 'reactstrap';
import {getSortState, JhiItemCount, JhiPagination} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntities} from './entrustment.reducer';
import {ITEMS_PER_PAGE} from 'app/shared/util/pagination.constants';

export interface IEntrustmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export const Entrustment = (props: IEntrustmentProps) => {
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

  const {entrustmentList, match, totalItems} = props;
  return (
    <div>
      <h2 id="entrustment-heading">
        Entrustments
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus"/>
          &nbsp; Create new Entrustment
        </Link>
      </h2>
      <div className="table-responsive">
        {entrustmentList && entrustmentList.length > 0 ? (
          <Table responsive>
            <thead>
            <tr>
              <th className="hand" onClick={sort('id')}>
                ID <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('hours')}>
                Hours <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('hoursMultiplier')}>
                Hours Multiplier <FontAwesomeIcon icon="sort"/>
              </th>
              <th>
                Entrustment Plan <FontAwesomeIcon icon="sort"/>
              </th>
              <th>
                Course Class <FontAwesomeIcon icon="sort"/>
              </th>
              <th>
                Teacher <FontAwesomeIcon icon="sort"/>
              </th>
              <th/>
            </tr>
            </thead>
            <tbody>
            {entrustmentList.map((entrustment, i) => (
              <tr key={`entity-${i}`}>
                <td>
                  <Button tag={Link} to={`${match.url}/${entrustment.id}`} color="link" size="sm">
                    {entrustment.id}
                  </Button>
                </td>
                <td>{entrustment.hours}</td>
                <td>{entrustment.hoursMultiplier}</td>
                <td>
                  {entrustment.entrustmentPlanId ? (
                    <Link
                      to={`entrustment-plan/${entrustment.entrustmentPlanId}`}>{entrustment.entrustmentPlanId}</Link>
                  ) : (
                    ''
                  )}
                </td>
                <td>
                  {entrustment.courseClassId ? (
                    <Link to={`course-class/${entrustment.courseClassId}`}>{entrustment.courseClassId}</Link>
                  ) : (
                    ''
                  )}
                </td>
                <td>{entrustment.teacherId ?
                  <Link to={`teacher/${entrustment.teacherId}`}>{entrustment.teacherId}</Link> : ''}</td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`${match.url}/${entrustment.id}`} color="info" size="sm">
                      <FontAwesomeIcon icon="eye"/> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${entrustment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                    >
                      <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${entrustment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          <div className="alert alert-warning">No Entrustments found</div>
        )}
      </div>
      <div className={entrustmentList && entrustmentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({entrustment}: IRootState) => ({
  entrustmentList: entrustment.entities,
  totalItems: entrustment.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Entrustment);
