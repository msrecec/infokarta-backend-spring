import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    showDynamicModal,
    hideDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

const beautifyHeader = (header) => {
    console.log(header);
    const regex = /([_])/g;
    const capitalisedHeader = header.charAt(0).toUpperCase() + header.slice(1);
    return capitalisedHeader.replaceAll(regex, ' ');
};

const formStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToEdit: PropTypes.object,
      fieldsToExclude: PropTypes.array,
      readOnlyFields: PropTypes.array,
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
              <Modal.Body style={formStyle}>
                  <Form>
                      {this.props.itemToEdit ? Object.entries(this.props.itemToEdit).map((entries) => {
                          if (!this.props.fieldsToExclude.includes(entries[0])) {
                              return (
                                  <FormGroup controlId={entries[0]} key={entries[0]}>
                                      <ControlLabel>{beautifyHeader(entries[0])}</ControlLabel>
                                      <FormControl as="input" defaultValue={typeof entries[1] === undefined ? '' : entries[1]} readOnly = {this.props.readOnlyFields.includes(entries[0])} />
                                  </FormGroup>
                              );
                          }
                          return null;
                      }
                      ) : null}
                  </Form>
              </Modal.Body>
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
