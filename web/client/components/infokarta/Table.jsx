import React from 'react';
import PropTypes from 'prop-types';
import { Table } from 'react-bootstrap';
import { cloneDeep } from "lodash";

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";

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

  constructor(props) {
      super(props);

      this.state = {};
  }

  componentDidUpdate(prevProps) {
      if ((prevProps.tableHeight !== this.props.tableHeight) && (this.props.tableHeight === "600px")) {
          // kad se ugasi details view, makni boju sa odabranog rowa
          let tableRow = document.getElementById(this.state.prevActiveRow);
          if (tableRow) {
              tableRow.style.background = "";
          }
          this.setState({});
      }
  }

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
                          <tr key={`b-tr-${item.id_hist ? item.id_hist : item.fid}`} id={`b-tr-${item.id_hist ? item.id_hist : item.fid}`} onClick={() => {this.sendRowDataToDetailsView(item); this.setActiveRow(`b-tr-${item.id_hist ? item.id_hist : item.fid}`);}}>
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

  sendRowDataToDetailsView(item) {
      this.props.zoomToItem(item.geom || item.fk);
      const temp = cloneDeep(item);
      if (temp.geom) {
          delete temp.geom;
      }
      this.props.sendDataToDetailsView(temp);
  }

  setActiveRow(key) {
      let tableRow;
      if (this.state.prevActiveRow) {
          // ako postoji aktivni kljuc, postavi mu boju na prazan string (potrebno da bootstrap hover radi)
          // i obojaj sljedeci
          tableRow = document.getElementById(this.state.prevActiveRow);
          if (tableRow) {
              tableRow.style.background = "";
          } // fix ako se promijeni stranica
          this.setState({ prevActiveRow: key });
          tableRow = document.getElementById(key);
          tableRow.style.background = "#999999";
      } else {
          // ako ne postoji, postavi novi kljuc u zelenu
          this.setState({ prevActiveRow: key });
          tableRow = document.getElementById(key);
          tableRow.style.background = "#999999";
      }
  }
}


export default TableComponent;

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

/* <td>
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
</td> */
