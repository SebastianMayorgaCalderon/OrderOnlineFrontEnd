import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOffers } from 'app/shared/model/offers.model';
import { getEntities as getOffers } from 'app/entities/offers/offers.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IOrders } from 'app/shared/model/orders.model';
import { getEntities as getOrders } from 'app/entities/orders/orders.reducer';
import { IDishes } from 'app/shared/model/dishes.model';
import { getEntities as getDishes } from 'app/entities/dishes/dishes.reducer';
import { getEntity, updateEntity, createEntity, reset } from './combos.reducer';
import { ICombos } from 'app/shared/model/combos.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICombosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICombosUpdateState {
  isNew: boolean;
  idsoffer: any[];
  restaurantId: string;
  combosId: string;
  dishesId: string;
}

export class CombosUpdate extends React.Component<ICombosUpdateProps, ICombosUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsoffer: [],
      restaurantId: '0',
      combosId: '0',
      dishesId: '0',
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

    this.props.getOffers();
    this.props.getRestaurants();
    this.props.getOrders();
    this.props.getDishes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { combosEntity } = this.props;
      const entity = {
        ...combosEntity,
        ...values,
        offers: mapIdList(values.offers)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/combos');
  };

  render() {
    const { combosEntity, offers, restaurants, orders, dishes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="orderOnlineFrontEndApp.combos.home.createOrEditLabel">
              <Translate contentKey="orderOnlineFrontEndApp.combos.home.createOrEditLabel">Create or edit a Combos</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : combosEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="combos-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="orderOnlineFrontEndApp.combos.name">Name</Translate>
                  </Label>
                  <AvField
                    id="combos-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="availableLabel" check>
                    <AvInput id="combos-available" type="checkbox" className="form-control" name="available" />
                    <Translate contentKey="orderOnlineFrontEndApp.combos.available">Available</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="offers">
                    <Translate contentKey="orderOnlineFrontEndApp.combos.offer">Offer</Translate>
                  </Label>
                  <AvInput
                    id="combos-offer"
                    type="select"
                    multiple
                    className="form-control"
                    name="offers"
                    value={combosEntity.offers && combosEntity.offers.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {offers
                      ? offers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="restaurant.id">
                    <Translate contentKey="orderOnlineFrontEndApp.combos.restaurant">Restaurant</Translate>
                  </Label>
                  <AvInput id="combos-restaurant" type="select" className="form-control" name="restaurantId">
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
                <AvGroup>
                  <Label for="combos.name">
                    <Translate contentKey="orderOnlineFrontEndApp.combos.combos">Combos</Translate>
                  </Label>
                  <AvInput id="combos-combos" type="select" className="form-control" name="combosId">
                    <option value="" key="0" />
                    {orders
                      ? orders.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/combos" replace color="info">
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
  offers: storeState.offers.entities,
  restaurants: storeState.restaurant.entities,
  orders: storeState.orders.entities,
  dishes: storeState.dishes.entities,
  combosEntity: storeState.combos.entity,
  loading: storeState.combos.loading,
  updating: storeState.combos.updating,
  updateSuccess: storeState.combos.updateSuccess
});

const mapDispatchToProps = {
  getOffers,
  getRestaurants,
  getOrders,
  getDishes,
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
)(CombosUpdate);
