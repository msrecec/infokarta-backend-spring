import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { get } from "lodash";
import {Button, Modal} from 'react-bootstrap';

import {
    hideGravePickModal,
    confirmGravePick,
    disableGravePickMode
} from "../../actions/infokarta/pokojnici";

import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

const style = {
    overflow: "auto",
    maxHeight: "500px"
};

const buttonContainerStyle = {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between"
};

class GravePicker extends React.Component {
  static propTypes = {
      show: PropTypes.bool,
      mode: PropTypes.string,
      chosenGrave: PropTypes.number,
      graveData: PropTypes.object,

      hideModal: PropTypes.func,
      loadGrave: PropTypes.func,
      endGravePickMode: PropTypes.func
  };

  static defaultProps = {
      graveData: {},
      show: false,
      mode: "initial"
  };

  render() {
      let modalHeader = "";
      let modalButtons = null;

      let discardButton = (
          <Button bsStyle="danger" onClick={() => this.props.endGravePickMode()}>
          Odustani od odabira grobnice
          </Button>
      );

      let cancelButtons = (
          <div style={buttonContainerStyle}>
              <Button bsStyle="warning" onClick={() => this.props.hideModal()}>
                U redu
              </Button>
              {discardButton}
          </div>
      );

      let decisionButtons = (
          <div style={buttonContainerStyle}>
              <div>
                  <Button bsStyle="success" onClick={() => this.props.loadGrave()}>
                    Da
                  </Button>
                  <Button bsStyle="info" onClick={() => this.props.hideModal()}>
                    Ne, odaberi drugu
                  </Button>
              </div>
              {discardButton}
          </div>
      );

      switch (this.props.mode) {
      case "single":
          modalHeader = "Jeste li sigurni da želite odabrati ovu grobnicu?";
          modalButtons = decisionButtons;
          break;
      case "multiple":
          modalHeader = "Zumirajte bliže i kliknite na jednu grobnicu.";
          modalButtons = cancelButtons;
          break;
      default:
          modalHeader = "Kliknite na grobnicu koju želite dodati.";
          modalButtons = cancelButtons;
          break;
      }

      return this.props.show ? (
          <Modal show={this.props.show} onHide={this.props.hideModal}>
              <Modal.Header closeButton>
                  <Modal.Title>{modalHeader}</Modal.Title>
              </Modal.Header>
              <Modal.Body style={style}>
                  {(this.props.graveData && this.props.graveData !== undefined) ? displayFeatureInfo(this.props.graveData) : null}
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
    loadGrave: confirmGravePick,
    endGravePickMode: disableGravePickMode
})(GravePicker);

export default GravePickerModal;
