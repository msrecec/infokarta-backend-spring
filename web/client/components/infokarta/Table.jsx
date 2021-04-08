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

let zoomTooltip = (value) => {
    return (
        <Tooltip id="tooltip-top">
            {(value) ? "Pronađi stavku na karti" : "Koordinate stavke ne postoje."}
        </Tooltip>
    );
};

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
                                  {/* glyphicon-eye-open za details/document view */}
                                  <OverlayTrigger placement="top" delay={{ show: 250, hide: 400 }} overlay={editTooltip}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.editHandler(item)}
                                      >
                                          <Glyphicon glyph="pencil"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td>
                              <td>
                                  <OverlayTrigger placement="top" overlay={zoomTooltip(item.geom || item.fk)}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.zoomToItem(item.geom || item.fk)}
                                          // TODO prominit zoom funkciju da prima dodatan parametar
                                          // po kojemu se razlikuje koji api poziv se salje
                                          disabled={(item.geom || item.fk && item.fk > 0) ? false : true}
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

  editHandler = (item) => {
      if (item.geom) {
          delete item.geom;
      }
      this.props.sendDataToEdit(item);
  };
}


export default TableComponent;
