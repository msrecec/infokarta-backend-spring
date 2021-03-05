import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form} from 'react-bootstrap';
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

const style = {
    display: "flex",
    justifyContent: "space-between"
};

const modalStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

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
              <Modal.Body style={modalStyle}>
                  {/* <Form> */}
                  {/* {this.props.itemToEdit ? Object.entries(this.props.itemToEdit).map((entries) =>
                          <Form.Group controlId={entries[0]}>
                              <Form.Label>{beautifyHeader(entries[0])}</Form.Label>
                              <Form.Control as="input" value={typeof entries[1] === undefined ? '' : entries[1]} />
                          </Form.Group>
                      ) : null} */}
                  <form>
                      {this.props.itemToEdit ? Object.entries(this.props.itemToEdit).map((entries) =>
                          <div style={style}>
                              <label>{beautifyHeader(entries[0])}</label>
                              <input type="text" value={typeof entries[1] === undefined ? "" : entries[1]} />
                          </div>
                      ) : null}

                  </form>
                  {/* </Form> */}
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
