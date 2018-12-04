import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './offers.reducer';
import { IOffers } from 'app/shared/model/offers.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOffersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OffersDetail extends React.Component<IOffersDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { offersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="orderOnlineFrontEndApp.offers.detail.title">Offers</Translate> [<b>{offersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="orderOnlineFrontEndApp.offers.name">Name</Translate>
              </span>
            </dt>
            <dd>{offersEntity.name}</dd>
            <dt>
              <span id="price">
                <Translate contentKey="orderOnlineFrontEndApp.offers.price">Price</Translate>
              </span>
            </dt>
            <dd>{offersEntity.price}</dd>
            <dt>
              <span id="image">
                <Translate contentKey="orderOnlineFrontEndApp.offers.image">Image</Translate>
              </span>
            </dt>
            <dd>
              {offersEntity.image ? (
                <div>
                  <a onClick={openFile(offersEntity.imageContentType, offersEntity.image)}>
                    <Translate contentKey="entity.action.open">Open</Translate>
                    &nbsp;
                  </a>
                  <span>
                    {offersEntity.imageContentType}, {byteSize(offersEntity.image)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.offers.dishes">Dishes</Translate>
            </dt>
            <dd>
              {offersEntity.dishes
                ? offersEntity.dishes.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === offersEntity.dishes.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.offers.combos">Combos</Translate>
            </dt>
            <dd>
              {offersEntity.combos
                ? offersEntity.combos.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === offersEntity.combos.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.offers.restaurant">Restaurant</Translate>
            </dt>
            <dd>{offersEntity.restaurantId ? offersEntity.restaurantId : ''}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.offers.offers">Offers</Translate>
            </dt>
            <dd>{offersEntity.offersName ? offersEntity.offersName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/offers" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/offers/${offersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ offers }: IRootState) => ({
  offersEntity: offers.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OffersDetail);
