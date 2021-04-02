import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get, isEmpty } from "lodash";

import {
    showInsertModal,
    showInsertConfirmationModal,
    clearDynamicComponentStore
} from "../../actions/infokarta/dynamicModalControl";

import { beautifyHeader } from "../../utils/infokarta/BeautifyUtil";

const formStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToInsert: PropTypes.array,
      fieldsToExclude: PropTypes.array,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      show: PropTypes.bool,
      extraForm: PropTypes.array,
      sendToConfirmationForm: PropTypes.func,
      itemToCheck: PropTypes.object
  };

  static defaultProps = {
      itemToInsert: [],
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
          <Modal show={this.props.show} onHide={this.props.hideModal} backdrop={'static'}>
              <Modal.Header closeButton>
                  <Modal.Title>Unos nove stavke</Modal.Title>
              </Modal.Header>
              <Modal.Body style={formStyle}>
                  <Form>
                      {this.props.extraForm ? this.props.extraForm : null}
                      <hr/>
                      {Object.entries(this.state).map((entry) => {
                          if (!this.props.fieldsToExclude.includes(entry[0])) {
                              return (
                                  <FormGroup controlId={entry[0]} key={entry[0]}>
                                      <ControlLabel>{beautifyHeader(entry[0])}</ControlLabel>
                                      <FormControl
                                          value={this.state[entry[0]]}
                                          onChange={(e) => this.handleChange(entry[0], e)}
                                      />
                                  </FormGroup>
                              );
                          }
                          return null;
                      })
                      }
                  </Form>
              </Modal.Body>
              <Modal.Footer>
                  <Button onClick={this.props.hideModal}>
                  Zatvori
                  </Button>
                  <Button bsStyle="success" onClick={() => this.props.sendToConfirmationForm(this.state)}>
                  Unesi stavku
                  </Button>
              </Modal.Footer>
          </Modal>
      );
  }

  handleChange(field, e) {
      this.setState({ [field]: e.target.value });
  }

  updateState = () => {
      if (isEmpty(this.props.itemToCheck)) {
          const obj = this.props.itemToInsert.reduce((accumulator, currentValue) => {
              accumulator[currentValue] = "";
              return accumulator;
          }, {});
          this.setState(obj);
      } else {
          this.setState(this.props.itemToCheck);
      }
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToInsert: get(state, 'dynamicModalControl.itemToInsert'),
        itemToCheck: get(state, 'dynamicModalControl.itemToCheck'),
        show: get(state, 'dynamicModalControl.insertModalVisible')
    };
}, {
    showModal: showInsertModal,
    hideModal: clearDynamicComponentStore,
    sendToConfirmationForm: showInsertConfirmationModal
})(BaseModalComponent);

export default ModalComponent;
