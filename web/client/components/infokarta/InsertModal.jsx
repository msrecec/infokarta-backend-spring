import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    showInsertModal,
    hideInsertModal
} from "../../actions/infokarta/dynamicModalControl";

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";

const formStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToInsert: PropTypes.object,
      fieldsToExclude: PropTypes.array,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      show: PropTypes.bool,
      insertItem: PropTypes.func,
      extraForm: PropTypes.func,
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
          <Modal show={this.props.show} onHide={this.props.hideModal}>
              <Modal.Header closeButton>
                  <Modal.Title>Unos nove stavke</Modal.Title>
              </Modal.Header>
              <Modal.Body style={formStyle}>
                  <Form>
                      <Button variant="primary" onClick={() => this.props.startChooseGraveMode()}>
                        Odaberi grobnicu sa karte
                      </Button>
                      {this.props.extraForm ?
                          this.props.extraForm.map((field) => {
                              return field.type === "text" ?
                                  ( // ako polje ima type = text, napravi klasično text input polje
                                      <FormGroup
                                          key={field.label}
                                          controlId={field.label}
                                      >
                                          <ControlLabel>{field.label}</ControlLabel>
                                          <FormControl
                                              type={field.type}
                                              value={this.state[field.value]}
                                              onChange={(e) => this.handleChange(field.value, e)}/>
                                      </FormGroup>)
                                  : ( // ako polje nema type = text, napravi select polje
                                      <FormGroup
                                          key={field.label}
                                          controlId={field.label}
                                      >
                                          <ControlLabel>{field.label}</ControlLabel>
                                          <FormControl
                                              componentClass={field.type}
                                              placeholder={this.state[field.selectValues[0]]}
                                              onChange={(e) => this.handleChange(field.value, e)}
                                          >
                                              {field.selectValues.map((option) => {
                                                  return <option value={option}>{option}</option>;
                                              })}
                                          </FormControl>
                                      </FormGroup>
                                  );
                          }
                          ) : null}
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
                  <Button variant="primary" onClick={() => this.props.insertItem(this.state)}>
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
    hideModal: hideInsertModal
})(BaseModalComponent);

export default ModalComponent;
