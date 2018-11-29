import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICombos } from 'app/shared/model/combos.model';
import { getEntities as getCombos } from 'app/entities/combos/combos.reducer';
import { IOffers } from 'app/shared/model/offers.model';
import { getEntities as getOffers } from 'app/entities/offers/offers.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IOrders } from 'app/shared/model/orders.model';
import { getEntities as getOrders } from 'app/entities/orders/orders.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './dishes.reducer';
import { IDishes } from 'app/shared/model/dishes.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDishesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDishesUpdateState {
  isNew: boolean;
  idscombos: any[];
  idsoffer: any[];
  restaurantId: string;
  categoryId: string;
  dishesId: string;
}

export class DishesUpdate extends React.Component<IDishesUpdateProps, IDishesUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idscombos: [],
      idsoffer: [],
      restaurantId: '0',
      categoryId: '0',
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

    this.props.getCombos();
    this.props.getOffers();
    this.props.getRestaurants();
    this.props.getCategories();
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
      const { dishesEntity } = this.props;
      const entity = {
        ...dishesEntity,
        ...values,
        combos: mapIdList(values.combos),
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
    this.props.history.push('/entity/dishes');
  };

  render() {
    const { dishesEntity, combos, offers, restaurants, categories, orders, loading, updating } = this.props;
    const { isNew } = this.state;

    const { image, imageContentType } = dishesEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="orderOnlineFrontEndApp.dishes.home.createOrEditLabel">
              <Translate contentKey="orderOnlineFrontEndApp.dishes.home.createOrEditLabel">Create or edit a Dishes</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dishesEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="dishes-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.name">Name</Translate>
                  </Label>
                  <AvField
                    id="dishes-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.description">Description</Translate>
                  </Label>
                  <AvField
                    id="dishes-description"
                    type="text"
                    name="description"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="availableLabel" check>
                    <AvInput id="dishes-available" type="checkbox" className="form-control" name="available" />
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.available">Available</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageLabel" for="image">
                      <Translate contentKey="orderOnlineFrontEndApp.dishes.image">Image</Translate>
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
                    <AvInput
                      type="hidden"
                      name="image"
                      value={image}
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="combos">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.combos">Combos</Translate>
                  </Label>
                  <AvInput
                    id="dishes-combos"
                    type="select"
                    multiple
                    className="form-control"
                    name="combos"
                    value={dishesEntity.combos && dishesEntity.combos.map(e => e.id)}
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
                  <Label for="offers">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.offer">Offer</Translate>
                  </Label>
                  <AvInput
                    id="dishes-offer"
                    type="select"
                    multiple
                    className="form-control"
                    name="offers"
                    value={dishesEntity.offers && dishesEntity.offers.map(e => e.id)}
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
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.restaurant">Restaurant</Translate>
                  </Label>
                  <AvInput id="dishes-restaurant" type="select" className="form-control" name="restaurantId">
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
                  <Label for="category.name">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.category">Category</Translate>
                  </Label>
                  <AvInput id="dishes-category" type="select" className="form-control" name="categoryId">
                    <option value="" key="0" />
                    {categories
                      ? categories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="dishes.name">
                    <Translate contentKey="orderOnlineFrontEndApp.dishes.dishes">Dishes</Translate>
                  </Label>
                  <AvInput id="dishes-dishes" type="select" className="form-control" name="dishesId">
                    <option value="" key="0" />
                    {orders
                      ? orders.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/dishes" replace color="info">
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
  combos: storeState.combos.entities,
  offers: storeState.offers.entities,
  restaurants: storeState.restaurant.entities,
  categories: storeState.category.entities,
  orders: storeState.orders.entities,
  dishesEntity: storeState.dishes.entity,
  loading: storeState.dishes.loading,
  updating: storeState.dishes.updating,
  updateSuccess: storeState.dishes.updateSuccess
});

const mapDispatchToProps = {
  getCombos,
  getOffers,
  getRestaurants,
  getCategories,
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
)(DishesUpdate);
