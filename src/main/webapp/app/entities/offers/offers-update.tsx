import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDishes } from 'app/shared/model/dishes.model';
import { getEntities as getDishes } from 'app/entities/dishes/dishes.reducer';
import { ICombos } from 'app/shared/model/combos.model';
import { getEntities as getCombos } from 'app/entities/combos/combos.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IOrders } from 'app/shared/model/orders.model';
import { getEntities as getOrders } from 'app/entities/orders/orders.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './offers.reducer';
import { IOffers } from 'app/shared/model/offers.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOffersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOffersUpdateState {
  isNew: boolean;
  idsdishes: any[];
  idscombos: any[];
  restaurantId: string;
  offersId: string;
}

export class OffersUpdate extends React.Component<IOffersUpdateProps, IOffersUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsdishes: [],
      idscombos: [],
      restaurantId: '0',
      offersId: '0',
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

    this.props.getDishes();
    this.props.getCombos();
    this.props.getRestaurants();
    this.props.getOrders();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { offersEntity } = this.props;
      const entity = {
        ...offersEntity,
        ...values,
        dishes: mapIdList(values.dishes),
        combos: mapIdList(values.combos)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/offers');
  };

  render() {
    const { offersEntity, dishes, combos, restaurants, orders, loading, updating } = this.props;
    const { isNew } = this.state;

    const { image, imageContentType } = offersEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="orderOnlineFrontEndApp.offers.home.createOrEditLabel">
              <Translate contentKey="orderOnlineFrontEndApp.offers.home.createOrEditLabel">Create or edit a Offers</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : offersEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="offers-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.name">Name</Translate>
                  </Label>
                  <AvField
                    id="offers-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.price">Price</Translate>
                  </Label>
                  <AvField
                    id="offers-price"
                    type="string"
                    className="form-control"
                    name="price"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageLabel" for="image">
                      <Translate contentKey="orderOnlineFrontEndApp.offers.image">Image</Translate>
                    </Label>
                    <br />
                    {image ? (
                      <div>
                        <a onClick={openFile(imageContentType, image)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imageContentType}, {byteSize(image)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('image')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_image" type="file" onChange={this.onBlobChange(false, 'image')} />
                    <AvInput type="hidden" name="image" value={image} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="availableLabel" check>
                    <AvInput id="offers-available" type="checkbox" className="form-control" name="available" />
                    <Translate contentKey="orderOnlineFrontEndApp.offers.available">Available</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="dishes">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.dishes">Dishes</Translate>
                  </Label>
                  <AvInput
                    id="offers-dishes"
                    type="select"
                    multiple
                    className="form-control"
                    name="dishes"
                    value={offersEntity.dishes && offersEntity.dishes.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {dishes
                      ? dishes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="combos">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.combos">Combos</Translate>
                  </Label>
                  <AvInput
                    id="offers-combos"
                    type="select"
                    multiple
                    className="form-control"
                    name="combos"
                    value={offersEntity.combos && offersEntity.combos.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {combos
                      ? combos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="restaurant.id">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.restaurant">Restaurant</Translate>
                  </Label>
                  <AvInput id="offers-restaurant" type="select" className="form-control" name="restaurantId">
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
                  <Label for="offers.name">
                    <Translate contentKey="orderOnlineFrontEndApp.offers.offers">Offers</Translate>
                  </Label>
                  <AvInput id="offers-offers" type="select" className="form-control" name="offersId">
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
                <Button tag={Link} id="cancel-save" to="/entity/offers" replace color="info">
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
  dishes: storeState.dishes.entities,
  combos: storeState.combos.entities,
  restaurants: storeState.restaurant.entities,
  orders: storeState.orders.entities,
  offersEntity: storeState.offers.entity,
  loading: storeState.offers.loading,
  updating: storeState.offers.updating,
  updateSuccess: storeState.offers.updateSuccess
});

const mapDispatchToProps = {
  getDishes,
  getCombos,
  getRestaurants,
  getOrders,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OffersUpdate);
