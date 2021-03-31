import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    showInsertConfirmationModal,
    hideInsertConfirmationModal
} from "../../actions/infokarta/dynamicModalControl";

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
      grave: PropTypes.object
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
                      <h3>Odabrana grobnica</h3>
                      {this.props.grave ? displayFeatureInfo(this.props.grave) : <ControlLabel>Nije odabrana grobnica.</ControlLabel>}
                      <hr/>
                      <h3>Pokojnikovi podaci</h3>
                      {this.props.itemToCheck ? displayFeatureInfo(this.props.itemToCheck /* , this.props.fieldsToExclude*/) : <ControlLabel>Nema podataka za prikaz.</ControlLabel>}
                  </Form>
              </Modal.Body>
              <Modal.Footer>
                  <Button onClick={() => this.props.returnToInsertModal(this.props.itemToCheck)}>
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
        itemToCheck: get(state, 'dynamicModalControl.itemToCheck'),
        show: get(state, 'dynamicModalControl.insertConfirmationModalVisible'),
        grave: get(state, 'gravePickerTool.graveData')
    };
}, {
    showModal: showInsertConfirmationModal,
    returnToInsertModal: hideInsertConfirmationModal
})(BaseModalComponent);

export default ModalComponent;
