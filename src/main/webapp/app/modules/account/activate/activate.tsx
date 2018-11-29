import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Translate, getUrlParameter } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { IRootState } from 'app/shared/reducers';
import { activateAction, reset } from './activate.reducer';
import { createWhenActivate } from '../../../entities/restaurant/restaurant.reducer';

const successAlert = (
  <Alert color="success">
    <Translate contentKey="activate.messages.success">
      <strong>Your user account has been activated.</strong> Please
    </Translate>
    <Link to="/login" className="alert-link">
      <Translate contentKey="global.messages.info.authenticated.link">sign in</Translate>
    </Link>
    .
  </Alert>
);

const failureAlert = (
  <Alert color="danger">
    <Translate contentKey="activate.messages.error">
      <strong>Your user could not be activated.</strong> Please use the registration form to sign up.
    </Translate>
  </Alert>
);

export interface IActivateProps extends StateProps, DispatchProps, RouteComponentProps<{ key: any }> {}

export class ActivatePage extends React.Component<IActivateProps> {
  state = {
    userkey: ''
  };
  componentWillUnmount() {
    this.props.reset();
  }
  componentWillMount() {
    const key = getUrlParameter('key', this.props.location.search);
    this.setState({ userkey: key });
  }
  activateAccount = (event, values) => {
    const key = this.state.userkey;
    debugger;
    this.props.activateAction(key, values.Restaurant);
  };

  render() {
    const { activationSuccess, activationFailure } = this.props;
    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h1>
              <Translate contentKey="activate.title">Activation</Translate>
            </h1>
            {activationSuccess ? successAlert : undefined}
            {activationFailure ? failureAlert : undefined}
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="2">
            <AvForm id="activation-form" onValidSubmit={this.activateAccount}>
              <AvField
                name="Restaurant"
                label={'Restaurante'}
                placeholder={'Nombre de Restaurante'}
                validate={{
                  required: { value: true, errorMessage: 'Porfavor indique el nombre del resturante' },
                  minLength: { value: 5, errorMessage: 'El nombre de restaurante debe tener por lo menos 5 caracteres' },
                  maxLength: { value: 50, errorMessage: 'El nombre de restaurante no debe exeder los 50 caracteres' }
                }}
              />
              <Button id="register-submit" color="primary" type="submit">
                Confirmar
              </Button>
            </AvForm>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ activate }: IRootState) => ({
  activationSuccess: activate.activationSuccess,
  activationFailure: activate.activationFailure
});

const mapDispatchToProps = { activateAction, reset, createWhenActivate };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivatePage);
