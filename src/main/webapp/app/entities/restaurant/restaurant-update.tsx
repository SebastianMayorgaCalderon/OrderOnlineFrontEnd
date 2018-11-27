import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRestaurantAdmin } from 'app/shared/model/restaurant-admin.model';
import { getEntities as getRestaurantAdmins } from 'app/entities/restaurant-admin/restaurant-admin.reducer';
import { getEntity, updateEntity, createEntity, reset } from './restaurant.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRestaurantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRestaurantUpdateState {
  isNew: boolean;
  restaurantAdminId: string;
}

export class RestaurantUpdate extends React.Component<IRestaurantUpdateProps, IRestaurantUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      restaurantAdminId: '0',
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

    this.props.getRestaurantAdmins();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { restaurantEntity } = this.props;
      const entity = {
        ...restaurantEntity,
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
    this.props.history.push('/entity/restaurant');
  };

  render() {
    const { restaurantEntity, restaurantAdmins, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="orderOnlineFrontEndApp.restaurant.home.createOrEditLabel">
              <Translate contentKey="orderOnlineFrontEndApp.restaurant.home.createOrEditLabel">Create or edit a Restaurant</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : restaurantEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="restaurant-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="orderOnlineFrontEndApp.restaurant.name">Name</Translate>
                  </Label>
                  <AvField
                    id="restaurant-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="restaurantAdmin.name">
                    <Translate contentKey="orderOnlineFrontEndApp.restaurant.restaurantAdmin">Restaurant Admin</Translate>
                  </Label>
                  <AvInput id="restaurant-restaurantAdmin" type="select" className="form-control" name="restaurantAdminId">
                    <option value="" key="0" />
                    {restaurantAdmins
                      ? restaurantAdmins.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/restaurant" replace color="info">
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
  restaurantAdmins: storeState.restaurantAdmin.entities,
  restaurantEntity: storeState.restaurant.entity,
  loading: storeState.restaurant.loading,
  updating: storeState.restaurant.updating,
  updateSuccess: storeState.restaurant.updateSuccess
});

const mapDispatchToProps = {
  getRestaurantAdmins,
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
)(RestaurantUpdate);
