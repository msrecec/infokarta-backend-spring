import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    showInsertModal,
    hideInsertModal,
    showInsertConfirmationModal
} from "../../actions/infokarta/dynamicModalControl";

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";
import {buildDynamicForm} from "../../utils/infokarta/ComponentConstructorUtil";

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
      sendToConfirmationForm: PropTypes.func,
      extraForm: PropTypes.array,
      startChooseGraveMode: PropTypes.func
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
                      {this.props.extraForm ? buildDynamicForm(this.props.extraForm) : null}
                      <hr/>
                      {this.props.itemToInsert ? this.props.itemToInsert.map((entry) => {
                          if (!this.props.fieldsToExclude.includes(entry)) {
                              return (
                                  <FormGroup controlId={entry} key={entry}>
                                      <ControlLabel>{beautifyHeader(entry)}</ControlLabel>
                                      <FormControl
                                          value={this.state.entry}
                                          onChange={(e) => this.handleChange(entry, e)}
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
                  <Button variant="primary" onClick={() => this.props.sendToConfirmationForm(this.state)}>
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
      const obj = this.props.itemToInsert.reduce((accumulator, currentValue) => {
          accumulator[currentValue] = "";
          return accumulator;
      }, {});
      this.setState(obj);
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToInsert: get(state, 'dynamicModalControl.itemToInsert'),
        show: get(state, 'dynamicModalControl.insertModalVisible')
    };
}, {
    showModal: showInsertModal,
    hideModal: hideInsertModal,
    sendToConfirmationForm: showInsertConfirmationModal
})(BaseModalComponent);

export default ModalComponent;
