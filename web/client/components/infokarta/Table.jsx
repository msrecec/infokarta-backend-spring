import React from 'react';
import PropTypes from 'prop-types';
import {Table, Button} from 'react-bootstrap';

const style = {
    overflow: "auto",
    maxHeight: "500px",
    maxWidth: "1400px"
};

// funkcija za uljepsat headere, mijenja underscore sa razmakon i povecava prvo slovo
// TODO maknit nakon sta se popravi baza
const beautifyHeader = (header) => {
    const regex = /([_])/g;
    const capitalisedHeader = header.charAt(0).toUpperCase() + header.slice(1);
    return capitalisedHeader.replaceAll(regex, ' ');
};

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array,
      fieldsToExclude: PropTypes.array,
      sendDataToEdit: PropTypes.func
  };

  render() {
      return (
          <div style={style}>
              <Table striped bordered condensed hover>
                  <thead>
                      <tr>
                          <th key="#" />
                          {/* koristi se tako da edit botun ne pomakne sve udesno za jedno misto */}
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
