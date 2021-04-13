import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from 'lodash';

import FileContainer from "./FileContainer";

import { getFilesByEntityId } from "../../../actions/infokarta/fileManagement";

class FileListComponent extends React.Component {
  static propTypes = {
      itemId: PropTypes.number,
      files: PropTypes.array,
      getFilesMeta: PropTypes.func
  };

  static defaultProps = {
      files: [],
      itemId: null
  };

  componentDidUpdate(prevProps) {
      if (prevProps.itemId !== this.props.itemId) {
          this.props.getFilesMeta("pokojnici", "slika", this.props.itemId);
          // this.props.getFilesMeta("pokojnici", "dokument", this.props.itemId);
          // TODO dodat dva polja, tj. niza od kojih jedan prikazuje slike a drugi dokumente
      }
  }

  render() {
      return (
          <div>
              {this.props.files.length !== 0 ? Object.entries(this.props.files).map((file) => {
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
