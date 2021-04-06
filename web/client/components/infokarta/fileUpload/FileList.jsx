import React from 'react';
import PropTypes from 'prop-types';

import FileContainer from "./FileContainer";

class FileList extends React.Component {
  static propTypes = {
      files: PropTypes.array
  };

  static defaultProps = {
      files: []
  };

  render() {
      return (
          <div>
              {this.props.files.length !== 0 ? Object.entries(this.props.files).map((file) => {
                  return (
                      <FileContainer controlId={file} key={file} />
                  );
              }
              ) : null}
          </div>
      );
  }
}

export default FileList;
