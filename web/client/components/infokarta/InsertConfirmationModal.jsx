import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    alternateModalVisibility
} from "../../actions/infokarta/dynamicComponents";

import {displayFeatureInfo} from "../../utils/infokarta/ComponentConstructorUtil";

const formStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToCheck: PropTypes.object,
      fieldsToExclude: PropTypes.array,
      show: PropTypes.bool,
      returnToInsertModal: PropTypes.func,
      insertItem: PropTypes.func,
      extraForm: PropTypes.object,
      insertModalName: PropTypes.string,
      insertConfirmationModalName: PropTypes.string
  };

  static defaultProps = {
      itemToCheck: [],
      show: false
  };

  render() {
      return (
          <Modal show={this.props.show} backdrop={'static'}>
              <Modal.Header>
                  <Modal.Title>Potvrda unosa</Modal.Title>
              </Modal.Header>
              <Modal.Body style={formStyle}>
                  <Form>
                      {this.props.extraForm ? this.props.extraForm : null}
                      {this.props.itemToCheck ? displayFeatureInfo(this.props.itemToCheck) : <ControlLabel>Nema podataka za prikaz.</ControlLabel>}
                  </Form>
              </Modal.Body>
              <Modal.Footer>
                  <Button onClick={() => this.props.returnToInsertModal(
                      this.props.insertConfirmationModalName,
                      this.props.insertModalName
                  )}>
                  Povratak
                  </Button>
                  <Button bsStyle="success" onClick={() => this.props.insertItem(this.props.itemToCheck)}>
                  Potvrdi unos
                  </Button>
              </Modal.Footer>
          </Modal>
      );
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToCheck: get(state, 'dynamicComponents.itemToCheck'),
        insertModalName: get(state, 'dynamicComponents.activePlugin') + 'Insert',
        insertConfirmationModalName: get(state, 'dynamicComponents.activePlugin') + 'Confirmation'
    };
}, {
    returnToInsertModal: alternateModalVisibility
})(BaseModalComponent);

export default ModalComponent;
