import React from 'react';
import PropTypes from 'prop-types';
import {Table} from 'react-bootstrap'

class TableComponent extends React.Component {
  static propTypes = {
    // headers: PropTypes.array,
    items: PropTypes.array
  };

  // TODO dodaj keyeve za elemente u listi
  render() {
    return (
      <div>
        <Table striped bordered hover>
          {/* <thead>
            <tr>
              {this.props.headers.map((header) => 
                <th>{header}</th>
              )}
            </tr>
          </thead> */}
          <tbody>
            {this.props.items.map((item) =>
              <tr>
                {item.map((field) =>
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
