import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import {Button} from 'react-bootstrap';
import { get } from "lodash";

// import {
//     enableGravePickModal,
//     disableGravePickModal,
//     confirmGravePick,
//     setGravePickMode
// } from "../../actions/infokarta/pokojnici";

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
        <Button variant="primary" onClick={() => this.console.log()}>
            Potvrdi
        </Button>
        <Button variant="primary" onClick={() => this.props.setChooseMode("initial", null)}>
            Odustani
        </Button>
    </div>
);

const style = {
    background: "white",
    position: "fixed",
    bottom: "0px",
    height: "100px",
    width: "100%",
    zIndex: 10000,
    display: "none"
};

function toggleDiv(show) {
    console.log('!!! toggleDiv', show);
    const x = document.getElementById("!!! customDiv");
    if (show === true) {
        console.log('!!! SHOW DIV');
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

class GravePicker extends React.Component {
  static propTypes = {
      show: PropTypes.bool,
      mode: PropTypes.string,
      chosenGrave: PropTypes.number,
      showModal: PropTypes.func,
      hideModal: PropTypes.func,
      loadGrave: PropTypes.func,
      setChooseMode: PropTypes.func
  };

  static defaultProps = {
      itemToEdit: {},
      show: false,
      mode: "initial"
  };

  componentDidUpdate(prevProps) {
      console.log('!!! entered CDU', prevProps, this.props);
      if (prevProps.show !== this.props.show) {
          console.log('!!! CDU - PROP CHANGED', this.props.show);
          toggleDiv(this.props.show);
      }
  }

  render() {
      console.log('!!! entered render');
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
          <div id="customDiv" style={style}>
              <h3>
                  {modalHeader}
              </h3>
              <div>
                  {modalButtons}
              </div>
          </div>
      );
  }
}

// const GravePickerComponent = connect((state) => {
//     return {
//         // show: get(state, 'pokojnici.chooseGraveModal'),
//         // mode: get(state, 'pokojnici.graveChooseMode'),
//         // chosenGrave: get(state, 'pokojnici.chosenGrave')
//     };
// }, {
//     // showModal: enableGravePickModal,
//     // hideModal: disableGravePickModal,
//     // loadGrave: confirmGravePick,
//     // setChooseMode: setGravePickMode
// })(GravePicker);

// export default GravePickerComponent;

export default GravePicker;
