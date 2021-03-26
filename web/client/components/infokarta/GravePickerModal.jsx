import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { get } from "lodash";
import {Button, Modal} from 'react-bootstrap';

import {
    hideGravePickModal,
    confirmGravePick
} from "../../actions/infokarta/pokojnici";

class GravePicker extends React.Component {
  static propTypes = {
      show: PropTypes.bool,
      mode: PropTypes.string,
      chosenGrave: PropTypes.number,
      graveData: PropTypes.object,

      hideModal: PropTypes.func,
      loadGrave: PropTypes.func
  };

  static defaultProps = {
      itemToEdit: {},
      show: false,
      mode: "initial"
  };

  render() {
      let modalHeader = "";
      let modalButtons = null;

      let cancelButton = (
          <div>
              <Button variant="danger" onClick={() => this.props.hideModal()}>
                U redu
              </Button>
          </div>
      );

      let decisionButtons = (
          <div>
              <Button variant="success" onClick={() => this.props.loadGrave()}>
                Da
              </Button>
              <Button variant="danger" onClick={() => this.props.hideModal()}>
                Ne, odaberi drugu
              </Button>
          </div>
      );

      switch (this.props.mode) {
      case "single":
          modalHeader = "Jeste li sigurni da želite odabrati ovu grobnicu?";
          modalButtons = decisionButtons;
          break;
      case "multiple":
          modalHeader = "Zumirajte i kliknite na samo jednu grobnicu.";
          modalButtons = cancelButton;
          break;
      default:
          modalHeader = "Kliknite na grobnicu koju želite dodati.";
          modalButtons = cancelButton;
          break;
      }

      return this.props.show ? (
          <Modal show={this.props.show} onHide={this.props.hideModal}>
              <Modal.Header closeButton>
                  <Modal.Title>{modalHeader}</Modal.Title>
              </Modal.Header>
              <Modal.Body>
                  {this.props.graveData ? this.props.graveData.id : ""}
              </Modal.Body>
              <Modal.Footer>
                  {modalButtons}
              </Modal.Footer>
          </Modal>
      ) : null;
  }
}

const GravePickerModal = connect((state) => {
    return {
        show: get(state, 'pokojnici.chooseGraveModal'),
        mode: get(state, 'pokojnici.graveChooseMode'),
        chosenGrave: get(state, 'pokojnici.chosenGrave'),
        graveData: get(state, 'pokojnici.graveData')
    };
}, {
    hideModal: hideGravePickModal,
    loadGrave: confirmGravePick
})(GravePicker);

export default GravePickerModal;
