import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form} from 'react-bootstrap';
import { get } from "lodash";

import {
    showDynamicModal,
    hideDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToEdit: PropTypes.object,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      show: PropTypes.bool
  };

  static defaultProps = {
      itemToEdit: {},
      show: false
  };

  render() {
      return (
          <Modal show={this.props.show} onHide={this.props.hideModal}>
              <Modal.Header closeButton>
                  <Modal.Title>Uređivanje stavke</Modal.Title>
              </Modal.Header>
              <Modal.Body>{this.props.itemToEdit.ime_i_prezime}</Modal.Body>
              <Modal.Footer>
                  <Button variant="secondary" onClick={this.props.hideModal}>
                  Zatvori
                  </Button>
                  <Button variant="primary" onClick={this.props.hideModal}> {/* TODO dodat poziv za edit */}
                  Spremi promjene
                  </Button>
              </Modal.Footer>
          </Modal>
      );
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToEdit: get(state, 'dynamicModalControl.itemToEdit'),
        show: get(state, 'dynamicModalControl.modalVisible')
    };
}, {
    showModal: showDynamicModal,
    hideModal: hideDynamicModal
})(BaseModalComponent);

export default ModalComponent;
