import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dishes.reducer';
import { IDishes } from 'app/shared/model/dishes.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDishesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DishesDetail extends React.Component<IDishesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dishesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Platillo [<b>{dishesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                Nombre
              </span>
            </dt>
            <dd>{dishesEntity.name}</dd>
            <dt>
              <span id="description">
                Descripción
              </span>
            </dt>
            <dd>{dishesEntity.description}</dd>
            <dt>
              <span id="available">
                Disponible
              </span>
            </dt>
            <dd>{dishesEntity.available ? 'Si' : 'No'}</dd>
            <dt>
              <span id="image">
                Imagen
              </span>
            </dt>
            <dd>
              {dishesEntity.image ? (
                <div>
                  <a onClick={openFile(dishesEntity.imageContentType, dishesEntity.image)}>
                    <Translate contentKey="entity.action.open">Open</Translate>
                    &nbsp;
                  </a>
                  <span>
                    {dishesEntity.imageContentType}, {byteSize(dishesEntity.image)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              Categoria
            </dt>
            <dd>{dishesEntity.categoryName ? dishesEntity.categoryName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/dishes" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/dishes/${dishesEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ dishes }: IRootState) => ({
  dishesEntity: dishes.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DishesDetail);
