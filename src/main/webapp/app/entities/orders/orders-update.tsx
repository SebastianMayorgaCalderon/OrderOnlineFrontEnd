import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './orders.reducer';
import { IOrders } from 'app/shared/model/orders.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrdersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrdersUpdateState {
  isNew: boolean;
  restaurantId: string;
}

export class OrdersUpdate extends React.Component<IOrdersUpdateProps, IOrdersUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      restaurantId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRestaurants();
  }

  saveEntity = (event, errors, values) => {
    values.date = new Date(values.date);

    if (errors.length === 0) {
      const { ordersEntity } = this.props;
      const entity = {
        ...ordersEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/orders');
  };

  render() {
    const { ordersEntity, restaurants, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="orderOnlineFrontEndApp.orders.home.createOrEditLabel">
              <Translate contentKey="orderOnlineFrontEndApp.orders.home.createOrEditLabel">Create or edit a Orders</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ordersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="orders-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="totalPriceLabel" for="totalPrice">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.totalPrice">Total Price</Translate>
                  </Label>
                  <AvField
                    id="orders-totalPrice"
                    type="string"
                    className="form-control"
                    name="totalPrice"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="subTotalPriceLabel" for="subTotalPrice">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.subTotalPrice">Sub Total Price</Translate>
                  </Label>
                  <AvField
                    id="orders-subTotalPrice"
                    type="string"
                    className="form-control"
                    name="subTotalPrice"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="iviLabel" for="ivi">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.ivi">Ivi</Translate>
                  </Label>
                  <AvField
                    id="orders-ivi"
                    type="string"
                    className="form-control"
                    name="ivi"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.date">Date</Translate>
                  </Label>
                  <AvInput
                    id="orders-date"
                    type="datetime-local"
                    className="form-control"
                    name="date"
                    value={isNew ? null : convertDateTimeFromServer(this.props.ordersEntity.date)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tableNumberLabel" for="tableNumber">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.tableNumber">Table Number</Translate>
                  </Label>
                  <AvField
                    id="orders-tableNumber"
                    type="string"
                    className="form-control"
                    name="tableNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="detailsLabel" for="details">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.details">Details</Translate>
                  </Label>
                  <AvField id="orders-details" type="text" name="details" />
                </AvGroup>
                <AvGroup>
                  <Label id="availableLabel" check>
                    <AvInput id="orders-available" type="checkbox" className="form-control" name="available" />
                    <Translate contentKey="orderOnlineFrontEndApp.orders.available">Available</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="restaurant.id">
                    <Translate contentKey="orderOnlineFrontEndApp.orders.restaurant">Restaurant</Translate>
                  </Label>
                  <AvInput id="orders-restaurant" type="select" className="form-control" name="restaurantId">
                    <option value="" key="0" />
                    {restaurants
                      ? restaurants.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/orders" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  restaurants: storeState.restaurant.entities,
  ordersEntity: storeState.orders.entity,
  loading: storeState.orders.loading,
  updating: storeState.orders.updating,
  updateSuccess: storeState.orders.updateSuccess
});

const mapDispatchToProps = {
  getRestaurants,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrdersUpdate);
