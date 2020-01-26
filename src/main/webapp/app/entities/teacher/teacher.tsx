import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Table} from 'reactstrap';
import {getSortState, JhiItemCount, JhiPagination} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntities} from './teacher.reducer';
import {ITEMS_PER_PAGE} from 'app/shared/util/pagination.constants';

export interface ITeacherProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export const Teacher = (props: ITeacherProps) => {
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

  const {teacherList, match, totalItems} = props;
  return (
    <div>
      <h2 id="teacher-heading">
        Teachers
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus"/>
          &nbsp; Create new Teacher
        </Link>
      </h2>
      <div className="table-responsive">
        {teacherList && teacherList.length > 0 ? (
          <Table responsive>
            <thead>
            <tr>
              <th className="hand" onClick={sort('id')}>
                ID <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('externalUserId')}>
                External User Id <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('firstName')}>
                First Name <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('lastName')}>
                Last Name <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('email')}>
                Email <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('hourLimit')}>
                Hour Limit <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('pensum')}>
                Pensum <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('agreedToAdditionalPensum')}>
                Agreed To Additional Pensum <FontAwesomeIcon icon="sort"/>
              </th>
              <th className="hand" onClick={sort('type')}>
                Type <FontAwesomeIcon icon="sort"/>
              </th>
              <th/>
            </tr>
            </thead>
            <tbody>
            {teacherList.map((teacher, i) => (
              <tr key={`entity-${i}`}>
                <td>
                  <Button tag={Link} to={`${match.url}/${teacher.id}`} color="link" size="sm">
                    {teacher.id}
                  </Button>
                </td>
                <td>{teacher.externalUserId}</td>
                <td>{teacher.firstName}</td>
                <td>{teacher.lastName}</td>
                <td>{teacher.email}</td>
                <td>{teacher.hourLimit}</td>
                <td>{teacher.pensum}</td>
                <td>{teacher.agreedToAdditionalPensum ? 'true' : 'false'}</td>
                <td>{teacher.type}</td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`${match.url}/${teacher.id}`} color="info" size="sm">
                      <FontAwesomeIcon icon="eye"/> <span className="d-none d-md-inline">View</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${teacher.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                    >
                      <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`${match.url}/${teacher.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          <div className="alert alert-warning">No Teachers found</div>
        )}
      </div>
      <div className={teacherList && teacherList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({teacher}: IRootState) => ({
  teacherList: teacher.entities,
  totalItems: teacher.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Teacher);
