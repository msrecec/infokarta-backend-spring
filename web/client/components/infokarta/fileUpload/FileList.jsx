import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from 'lodash';

import { Button } from 'react-bootstrap';

import FileContainer from "./FileContainer";

import { getFilesByEntityId } from "../../../actions/infokarta/fileManagement";

class FileListComponent extends React.Component {
  static propTypes = {
      files: PropTypes.array,
      getFilesMeta: PropTypes.func
  };

  static defaultProps = {
      files: []
  };

  render() {
      return (
          <div>
              <Button bsStyle="success" onClick={() => this.props.getFilesMeta("pokojnici", "slika", 1)}>get</Button>
              {this.props.files.length !== 0 ? Object.entries(this.props.files).map((file) => {
                  console.log(file[1], '!!! file');
                  return (
                      <FileContainer file={file[1]} />
                  );
              }
              ) : null}
          </div>
      );
  }
}

const FileList = connect((state) => {
    return {
        files: get(state, 'fileManagement.files')
    };
}, {
    getFilesMeta: getFilesByEntityId
})(FileListComponent);

export default FileList;
