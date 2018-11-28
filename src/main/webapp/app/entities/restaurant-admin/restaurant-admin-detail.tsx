import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './restaurant-admin.reducer';
import { IRestaurantAdmin } from 'app/shared/model/restaurant-admin.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRestaurantAdminDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RestaurantAdminDetail extends React.Component<IRestaurantAdminDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { restaurantAdminEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="orderOnlineFrontEndApp.restaurantAdmin.detail.title">RestaurantAdmin</Translate> [
            <b>{restaurantAdminEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="orderOnlineFrontEndApp.restaurantAdmin.name">Name</Translate>
              </span>
            </dt>
            <dd>{restaurantAdminEntity.name}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.restaurantAdmin.user">User</Translate>
            </dt>
            <dd>{restaurantAdminEntity.userId ? restaurantAdminEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/restaurant-admin" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/restaurant-admin/${restaurantAdminEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ restaurantAdmin }: IRootState) => ({
  restaurantAdminEntity: restaurantAdmin.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RestaurantAdminDetail);
