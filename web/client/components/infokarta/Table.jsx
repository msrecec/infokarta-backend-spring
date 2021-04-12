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

let zoomTooltip = (fid) => {
    return (
        <Tooltip id="tooltip-top">
            {fid > 0 ? "Pronađi stavku na karti" : "Koordinate stavke ne postoje."}
        </Tooltip>
    );
};

let detailsTooltip = (
    <Tooltip id="tooltip-top">
        Pregledaj detalje i dokumente vezane uz stavku
    </Tooltip>
);

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array,
      fieldsToExclude: PropTypes.array,
      sendDataToEdit: PropTypes.func,
      zoomToItem: PropTypes.func,
      sendDataToDetailsPlugin: PropTypes.func
  };

  render() {
      return (
          <div style={style}>
              <Table striped bordered condensed hover>
                  <thead>
                      <tr>
                          <th key="#" />
                          <th key="##" />
                          <th key="###" />
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
                          <tr onClick={() => this.props.zoomToItem(item.fk)}>
                              <td>
                                  <OverlayTrigger placement="top" overlay={detailsTooltip}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.sendDataToDetailsPlugin(item)}
                                      >
                                          <Glyphicon glyph="eye-open"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td>
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
                                  <OverlayTrigger placement="top" overlay={zoomTooltip(item.fk)}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.zoomToItem(item.fk)}
                                          // TODO prominit zoom funkciju da prima dodatan parametar
                                          // po kojemu se razlikuje koji api poziv se salje
                                          disabled={item.fk > 0 ? false : true}
                                      >
                                          <Glyphicon glyph="zoom-to"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td>
                              {/* funkcije na botunu tribaju bit pozvane priko arrow fje inace se pozove svaka na svakom botunu kad se on rendera */}
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
