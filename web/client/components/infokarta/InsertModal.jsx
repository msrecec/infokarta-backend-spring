import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';
import { get } from "lodash";

import {
    showInsertModal,
    hideDynamicModal
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
      insertItem: PropTypes.func
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
                      {this.props.itemToInsert ? this.props.itemToInsert.map((entry) => {
                          if (!this.props.fieldsToExclude.includes(entry)) {
                              return (
                                  <FormGroup controlId={entry} key={entry}>
                                      <ControlLabel>{beautifyHeader(entry)}</ControlLabel>
                                      <FormControl
                                          value={""}
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
      Object.entries(this.props.itemToInsert).map((entry) => {
          this.setState({ [entry[0]]: entry[1] });
          // ucitaj sve podatke u state komponente
      });
  }
}

const ModalComponent = connect((state) => {
    return {
        itemToInsert: get(state, 'dynamicModalControl.itemToInsert'),
        show: get(state, 'dynamicModalControl.insertModalVisible')
    };
}, {
    showModal: showInsertModal,
    hideModal: hideDynamicModal
})(BaseModalComponent);

export default ModalComponent;
