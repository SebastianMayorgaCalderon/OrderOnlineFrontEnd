import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './orders.reducer';
import { IOrders } from 'app/shared/model/orders.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrdersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrdersDetail extends React.Component<IOrdersDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ordersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="orderOnlineFrontEndApp.orders.detail.title">Orders</Translate> [<b>{ordersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="totalPrice">
                <Translate contentKey="orderOnlineFrontEndApp.orders.totalPrice">Total Price</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.totalPrice}</dd>
            <dt>
              <span id="subTotalPrice">
                <Translate contentKey="orderOnlineFrontEndApp.orders.subTotalPrice">Sub Total Price</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.subTotalPrice}</dd>
            <dt>
              <span id="ivi">
                <Translate contentKey="orderOnlineFrontEndApp.orders.ivi">Ivi</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.ivi}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="orderOnlineFrontEndApp.orders.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ordersEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tableNumber">
                <Translate contentKey="orderOnlineFrontEndApp.orders.tableNumber">Table Number</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.tableNumber}</dd>
            <dt>
              <span id="details">
                <Translate contentKey="orderOnlineFrontEndApp.orders.details">Details</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.details}</dd>
            <dt>
              <span id="available">
                <Translate contentKey="orderOnlineFrontEndApp.orders.available">Available</Translate>
              </span>
            </dt>
            <dd>{ordersEntity.available ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.orders.restaurant">Restaurant</Translate>
            </dt>
            <dd>{ordersEntity.restaurantId ? ordersEntity.restaurantId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/orders" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/orders/${ordersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orders }: IRootState) => ({
  ordersEntity: orders.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrdersDetail);
