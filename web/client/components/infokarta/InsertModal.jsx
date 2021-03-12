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
      itemToInsert: PropTypes.object,
      fieldsToExclude: PropTypes.array,
      readOnlyFields: PropTypes.array,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      show: PropTypes.bool,
      insertItem: PropTypes.func
  };

  static defaultProps = {
      itemToInsert: {},
      show: false
  };

  constructor(props) {
      super(props);
      this.handleChange = this.handleChange.bind(this);

      this.state = {};
  }

  componentDidUpdate(prevProps) {
      if (prevProps.show !== this.props.show) {
          this.updateState();
          // svaki put kad se promijeni vrijednost props.show (tj. kad se prikaze komponenta)
          // zove se funkcija za ucitat podatke u lokalni state
          // oni se kasnije salju u api poziv za insert
      }
  }

  render() {
      return (
          <Modal show={this.props.show} onHide={this.props.hideModal}>
              <Modal.Header closeButton>
                  <Modal.Title>Unos nove stavke</Modal.Title>
              </Modal.Header>
              <Modal.Body style={formStyle}>
                  <Form>
                      {this.props.itemToInsert ? Object.entries(this.props.itemToInsert).map((entries) => {
                          if (!this.props.fieldsToExclude.includes(entries[0])) {
                              return (
                                  <FormGroup controlId={entries[0]} key={entries[0]}>
                                      <ControlLabel>{beautifyHeader(entries[0])}</ControlLabel>
                                      <FormControl
                                          value={this.state[entries[0]]}
                                          onChange={(e) => this.handleChange(entries[0], e)}
                                          readOnly={this.props.readOnlyFields.includes(entries[0])}
                                      />
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
                  <Button variant="primary" onClick={() => this.props.insertItem(this.state)}>
                  Spremi promjene
                  </Button>
              </Modal.Footer>
          </Modal>
      );
  }

  handleChange(field, e) {
      this.setState({ [field]: e.target.value });
  }

  updateState = () => {
      Object.entries(this.props.itemToInsert).map((entry) => {
          this.setState({ [entry[0]]: entry[1] });
          // ucitaj sve podatke u state komponente
      });
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToInsert: get(state, 'dynamicModalControl.itemToInsert'),
        show: get(state, 'dynamicModalControl.modalVisible')
    };
}, {
    showModal: showDynamicModal,
    hideModal: hideDynamicModal
})(BaseModalComponent);

export default ModalComponent;
