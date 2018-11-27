import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './price-per-product.reducer';
import { IPricePerProduct } from 'app/shared/model/price-per-product.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPricePerProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PricePerProductDetail extends React.Component<IPricePerProductDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pricePerProductEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="orderOnlineFrontEndApp.pricePerProduct.detail.title">PricePerProduct</Translate> [
            <b>{pricePerProductEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="price">
                <Translate contentKey="orderOnlineFrontEndApp.pricePerProduct.price">Price</Translate>
              </span>
            </dt>
            <dd>{pricePerProductEntity.price}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="orderOnlineFrontEndApp.pricePerProduct.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={pricePerProductEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.pricePerProduct.productDish">Product Dish</Translate>
            </dt>
            <dd>{pricePerProductEntity.productDishId ? pricePerProductEntity.productDishId : ''}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.pricePerProduct.productCombo">Product Combo</Translate>
            </dt>
            <dd>{pricePerProductEntity.productComboId ? pricePerProductEntity.productComboId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/price-per-product" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/price-per-product/${pricePerProductEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ pricePerProduct }: IRootState) => ({
  pricePerProductEntity: pricePerProduct.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PricePerProductDetail);
