import React from 'react';
import PropTypes from 'prop-types';
import {Table, Button, Glyphicon, Tooltip, OverlayTrigger} from 'react-bootstrap';

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";

const style = {
    overflow: "auto",
    maxHeight: "600px",
    minWidth: "580px"
};

let editTooltip = (
    <Tooltip id="tooltip-top">
        Uredi stavku
    </Tooltip>
);

let zoomTooltip = (
    <Tooltip id="tooltip-top">
        Pronađi na karti
    </Tooltip>
);

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array,
      fieldsToExclude: PropTypes.array,
      sendDataToEdit: PropTypes.func,
      zoomToItem: PropTypes.func
  };

  render() {
      return (
          <div style={style}>
              <Table striped bordered condensed hover>
                  <thead>
                      <tr>
                          <th key="#" />
                          <th key="##" />
                          {/* koristi se tako da botuni ne pomaknu sve udesno za jedno misto */}
                          {this.props.items[0] ?
                              Object.keys(this.props.items[0]).map((header) => {
                                  if (!this.props.fieldsToExclude.includes(header)) {
                                      return (
                                          <th key={header}>{beautifyHeader(header)}</th>
                                      );
                                  }
                                  return null;
                              }
                              ) : null}
                      </tr>
                  </thead>
                  <tbody>
                      {this.props.items.map((item) =>
                          <tr>
                              <td>
                                  <OverlayTrigger placement="top" overlay={editTooltip}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.sendDataToEdit(item)}
                                      >
                                          <Glyphicon glyph="pencil"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td>
                              <td>
                                  <OverlayTrigger placement="top" overlay={zoomTooltip}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.zoomToItem(item.fk)}
                                          disabled={item.fk === 0 ? true : false}
                                      >
                                          <Glyphicon glyph="zoom-to"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td>
                              {/* funkcije na botunu tribaju bit pozvane priko arrow fje inace se pozove svaka na svakom botunu kad se on rendera */}
                              {/* TODO dodat tooltipove na ikonice od botuna */}
                              {/* TODO dodat pin kad se zumira */}
                              {Object.entries(item).map((field) => {
                                  if (!this.props.fieldsToExclude.includes(field[0])) {
                                      return (
                                          <td>{field[1]}</td>
                                      );
                                  }
                                  return null;
                              }
                              )}
                          </tr>
                      )}
                  </tbody>
              </Table>
          </div>
      );
  }
}

export default TableComponent;
