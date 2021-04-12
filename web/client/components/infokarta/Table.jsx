import React from 'react';
import PropTypes from 'prop-types';
import {Table, Button, Glyphicon, Tooltip, OverlayTrigger} from 'react-bootstrap';
import { cloneDeep } from "lodash";

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";

// let editTooltip = (
//     <Tooltip id="tooltip-top">
//         Uredi stavku
//     </Tooltip>
// );

// let zoomTooltip = (fid) => {
//     return (
//         <Tooltip id="tooltip-top">
//             {fid > 0 ? "Pronađi stavku na karti" : "Koordinate stavke ne postoje."}
//         </Tooltip>
//     );
// };

// let detailsTooltip = (
//     <Tooltip id="tooltip-top">
//         Pregledaj detalje i dokumente vezane uz stavku
//     </Tooltip>
// );

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array,
      fieldsToInclude: PropTypes.array,
      sendDataToDetailsView: PropTypes.func,
      zoomToItem: PropTypes.func,
      tableHeight: PropTypes.string
  };

  static defaultProps = {
      tableHeight: "600px"
  };

  render() {
      const style = {
          overflow: "auto",
          maxHeight: this.props.tableHeight,
          minWidth: "580px",
          transition: "all .2s linear",
          border: "1px solid #dddddd"
      };
      const thStyle = {
          position: "sticky",
          top: "0",
          background: "white",
          boxShadow: "0 2px 2px -1px #dddddd"
      };
      // https://css-tricks.com/position-sticky-and-table-headers/

      return (
          <div style={style}>
              <Table condensed hover style={{margin: "0"}}>
                  <thead>
                      <tr>
                          {this.props.items[0] ?
                              Object.keys(this.props.items[0]).map((header) => {
                                  if (this.props.fieldsToInclude.includes(header)) {
                                      return (
                                          <th style={thStyle}>{beautifyHeader(header)}</th>
                                      );
                                  }
                                  return null;
                              }
                              ) : null}
                      </tr>
                  </thead>
                  <tbody>
                      {this.props.items.map((item) =>
                          <tr onClick={() => this.tableRowClickHandler(item)}>
                              {/* TODO prominit u funkciju koja salje item u details and documents */}
                              {/* <td>
                                  <OverlayTrigger placement="top" overlay={detailsTooltip}>
                                      <Button
                                          bsStyle="primary"
                                          onClick={() => this.props.sendDataToDetailsView(item)}
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
                                          <Glyphicon glyph="map-marker"/>
                                      </Button>
                                  </OverlayTrigger>
                              </td> */}
                              {/* funkcije na botunu tribaju bit pozvane priko arrow fje inace se pozove svaka na svakom botunu kad se on rendera */}
                              {Object.entries(item).map((field) => {
                                  if (this.props.fieldsToInclude.includes(field[0])) {
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

  tableRowClickHandler(item) {
      this.props.zoomToItem(item.geom || item.fk);
      const temp = cloneDeep(item);
      if (temp.geom) {
          delete temp.geom;
      }
      this.props.sendDataToDetailsView(temp);
  }
}

export default TableComponent;
