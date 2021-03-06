import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {Button, FormGroup, ControlLabel} from 'react-bootstrap';

import { uploadNewImageByEntityId} from "../../../actions/infokarta/fileManagement";
import { insertSuccessful, insertUnsuccessful } from "../../../actions/infokarta/dynamicComponents";

class FileUploadFormComponent extends React.Component {
    static propTypes = {
        responseStatus: PropTypes.number,
        showSuccessMessage: PropTypes.func,
        showFailureMessage: PropTypes.func,
        uploadFile: PropTypes.func,
        itemId: PropTypes.number
    };

    static defaultProps = {
        responseStatus: null,
        itemId: null
    };

    constructor(props) {
        super(props);
        this.state = {
            file: {},
            fileChosen: false
        };
    }

    render() {
        const saveFileToLocalState = (e) => {
            const uploadedFile = e.target.files[0];
            this.setState({ "file": uploadedFile, "fileChosen": true });
        };

        const handleUpload = () => {
            if (this.state.file) {
                this.props.uploadFile("slika", this.state.file, this.props.itemId);
                this.setState({ "fileChosen": false });
            } else {
                this.props.showFailureMessage("Greška", "Niste odabrali dokument/sliku za prijenos.");
            }
        };

        return (
            <form style={{maxWidth: "500px"}}>
                <FormGroup
                    key="uploadForm"
                    controlId="uploadForm"
                >
                    <ControlLabel>Prenesite novi dokument ili sliku</ControlLabel>
                    <input type="file" onChange={saveFileToLocalState} />
                    <Button bsStyle="success" onClick={handleUpload} disabled={!this.state.fileChosen}>Prenesi dokument</Button>
                </FormGroup>
            </form>
        );
    }
}

const FileUploadForm = connect((state) => {
    return {
        responseStatus: get(state, 'fileManagement.uploadResponse')
    };
}, {
    uploadFile: uploadNewImageByEntityId,
    showSuccessMessage: insertSuccessful,
    showFailureMessage: insertUnsuccessful
})(FileUploadFormComponent);

export default FileUploadForm;
