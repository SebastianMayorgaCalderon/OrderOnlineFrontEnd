import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './combos.reducer';
import { ICombos } from 'app/shared/model/combos.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICombosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CombosDetail extends React.Component<ICombosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { combosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="orderOnlineFrontEndApp.combos.detail.title">Combos</Translate> [<b>{combosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="orderOnlineFrontEndApp.combos.name">Name</Translate>
              </span>
            </dt>
            <dd>{combosEntity.name}</dd>
            <dt>
              <span id="available">
                <Translate contentKey="orderOnlineFrontEndApp.combos.available">Available</Translate>
              </span>
            </dt>
            <dd>{combosEntity.available ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.combos.dishes">Dishes</Translate>
            </dt>
            <dd>
              {combosEntity.dishes
                ? combosEntity.dishes.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === combosEntity.dishes.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.combos.restaurant">Restaurant</Translate>
            </dt>
            <dd>{combosEntity.restaurantId ? combosEntity.restaurantId : ''}</dd>
            <dt>
              <Translate contentKey="orderOnlineFrontEndApp.combos.combos">Combos</Translate>
            </dt>
            <dd>{combosEntity.combosName ? combosEntity.combosName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/combos" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/combos/${combosEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ combos }: IRootState) => ({
  combosEntity: combos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CombosDetail);
