import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal, Form} from 'react-bootstrap';
import { get } from "lodash";

import {
    showInsertConfirmationModal,
    hideInsertConfirmationModal
} from "../../actions/infokarta/dynamicModalControl";

import {buildDynamicForm, displayFeatureInfo} from "../../utils/infokarta/ComponentConstructorUtil";

const formStyle = {
    overflow: "auto",
    maxHeight: "500px"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      itemToCheck: PropTypes.array,
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

  //   componentDidUpdate(prevProps) {
  //       if (prevProps.show !== this.props.show) {
  //           this.updateState();
  //       }
  //   }

  render() {
      return (
          <Modal show={this.props.show} backdrop={'static'}>
              <Modal.Header>
                  <Modal.Title>Potvrda unosa</Modal.Title>
              </Modal.Header>
              <Modal.Body style={formStyle}>
                  <Form>
                      <h3>Odabrana grobnica</h3>
                      {this.props.grave ? displayFeatureInfo(this.props.grave) : <span>Nije odabrana grobnica.</span>}
                      <hr/>
                      <h3>Pokojnikovi podaci</h3>
                      {this.props.itemToCheck ? buildDynamicForm(this.props.itemToCheck, this.props.fieldsToExclude) : <span>Nema podataka za prikaz.</span>}
                  </Form>
              </Modal.Body>
              <Modal.Footer>
                  <Button variant="secondary" onClick={this.props.returnToInsertModal(this.props.itemToCheck)}>
                  Povratak
                  </Button>
                  <Button variant="primary" onClick={() => this.props.insertItem(this.props.itemToCheck)}>
                  Potvrdi unos
                  </Button>
              </Modal.Footer>
          </Modal>
      );
  }

//   updateState = () => {
//       const obj = this.props.itemToCheck.reduce((accumulator, currentValue) => {
//           accumulator[currentValue] = "";
//           return accumulator;
//       }, {});
//       this.setState(obj);
//   }
}

const ModalComponent = connect((state) => {
    return {
        itemToCheck: get(state, 'dynamicModalControl.itemToCheck'),
        show: get(state, 'dynamicModalControl.insertConfirmationModalVisible'),
        grave: get(state, 'pokojnici.graveData')
    };
}, {
    showModal: showInsertConfirmationModal,
    returnToInsertModal: hideInsertConfirmationModal
})(BaseModalComponent);

export default ModalComponent;
