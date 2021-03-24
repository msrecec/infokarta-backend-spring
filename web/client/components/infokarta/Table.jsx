import React from 'react';
import PropTypes from 'prop-types';
import {Table, Button} from 'react-bootstrap';

import {beautifyHeader} from "../../utils/infokarta/BeautifyUtil";

const style = {
    overflow: "auto",
    maxHeight: "600px",
    minWidth: "580px"
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
                              <td><Button variant="Primary" onClick={() => this.props.sendDataToEdit(item)}>Uredi</Button></td>
                              <td><Button variant="Primary" onClick={() => this.props.zoomToItem(item.fk)}>Prikaži</Button></td>
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
