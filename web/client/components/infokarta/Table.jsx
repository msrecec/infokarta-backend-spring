import React from 'react';
import PropTypes from 'prop-types';
import {Table} from 'react-bootstrap';

class TableComponent extends React.Component {
  static propTypes = {
      items: PropTypes.array
  };

  // TODO dodaj keyeve za elemente u listi
  render() {
      return (
          <div>
              <Table striped bordered hover>
                  <thead>
                      <tr>
                          {this.props.items[0] ?
                              Object.keys(this.props.items[0]).map((header) =>
                                  <th>{header}</th>
                              ) : null}
                      </tr>
                  </thead>
                  <tbody>
                      {this.props.items.map((item) =>
                          <tr>
                              {Object.values(item).map((field) =>
                                  <td>{field}</td>
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
