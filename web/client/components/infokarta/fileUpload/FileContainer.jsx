import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from 'lodash';

import FileUploadForm from './FileUploadForm';
import FileList from './FileList';

import {
    getFilesByEntityId
} from "../../../actions/infokarta/fileManagement";

import { Button } from 'react-bootstrap';

class FileContainerComponent extends React.Component {
    static propTypes = {
        files: PropTypes.array,
        getFilesMeta: PropTypes.func
    };

    render() {
        const fileUpload = (<FileUploadForm
            files={this.props.files}
        />);

        const fileList = (<FileList
            files={this.props.files ? this.props.files : []}
        />);

        return (
            <div>
                <Button bsStyle="success" onClick={() => this.props.getFilesMeta(1)}>get</Button>
                {fileUpload}
                {fileList}
            </div>
        );
    }
}

const FileContainer = connect((state) => {
    return {
        files: get(state, 'fileManagement.files')
    };
}, {
    getFilesMeta: getFilesByEntityId
})(FileContainerComponent);

export default FileContainer;
