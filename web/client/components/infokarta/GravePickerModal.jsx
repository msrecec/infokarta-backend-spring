import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button, Modal} from 'react-bootstrap';
import { get } from "lodash";

import {
    enableGravePickModal,
    disableGravePickModal,
    confirmGravePick
} from "../../actions/infokarta/pokojnici";

let modalHeader = "";
let modalButtons = null;
let cancelButton = (
    <div>
        <Button variant="primary" onClick={() => this.props.hideModal()}>
            Odustani
        </Button>
    </div>
);

let decisionButtons = (
    <div>
        <Button variant="primary" onClick={() => this.props.loadGrave()}>
            Potvrdi
        </Button>
        <Button variant="primary" onClick={() => this.props.hideModal()}>
            Odustani
        </Button>
    </div>
);

const style = {
    // position: "fixed",
    // bottom: "0px",
    // height: "100px",
    // width: "100px"
    // zIndex: "10000"
};

class BaseModalComponent extends React.Component {
  static propTypes = {
      show: PropTypes.bool,
      mode: PropTypes.string,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      loadGrave: PropTypes.func
  };

  static defaultProps = {
      itemToEdit: {},
      show: false,
      mode: "initial"
  };

  render() {
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

      return (
          <Modal backdrop={false} keyboard={false} style={style} show={this.props.show} /* onHide={this.props.hideModal} */ >
              <Modal.Header>
                  {modalHeader}
              </Modal.Header>
              {/* <Modal.Body>
        </Modal.Body> */}
              <Modal.Footer>
                  {modalButtons}
              </Modal.Footer>
          </Modal>
      );
  }
}

const ModalComponent = connect((state) => {
    return {
        show: get(state, 'pokojnici.chooseGraveModal'),
        mode: get(state, 'pokojnici.graveChooseMode')
    };
}, {
    showModal: enableGravePickModal,
    hideModal: disableGravePickModal,
    loadGrave: confirmGravePick
})(BaseModalComponent);

export default ModalComponent;
