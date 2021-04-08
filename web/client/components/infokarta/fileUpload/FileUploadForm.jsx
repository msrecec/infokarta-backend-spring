import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {Button, FormGroup, ControlLabel} from 'react-bootstrap';

import { uploadNewFileByEntityId } from "../../../actions/infokarta/fileManagement";
import { insertSuccessful, insertUnsuccessful } from "../../../actions/infokarta/dynamicModalControl";

class FileUploadFormComponent extends React.Component {
    static propTypes = {
        responseStatus: PropTypes.number,
        showSuccessMessage: PropTypes.func,
        showFailureMessage: PropTypes.func,
        uploadFile: PropTypes.func
    };

    static defaultProps = {
        responseStatus: null
    };

    constructor(props) {
        super(props);
        this.state = {
            file: {},
            fileChosen: false
        };
    }

    componentDidUpdate(prevProps) {
        if (prevProps.responseStatus !== this.props.responseStatus) {
            if (this.props.responseStatus === 200) {
                this.props.showSuccessMessage("Prijenos podataka uspješan", "Vaš dokument/slika je uspješno pohranjen/a u bazu podataka.");
            } else if (this.props.responseStatus === 415) {
                this.props.showFailureMessage("Greška", "Format odabrane datoteke nije podržan.");
            } else {
                this.props.showFailureMessage("Greška", "Došlo je do greške prilikom prijenosa. Molimo pokušajte ponovno.");
            }
        }
    }

    render() {
        const saveFileToLocalState = (e) => {
            const uploadedFile = e.target.files[0];
            this.setState({ "file": uploadedFile, "fileChosen": true });
        };

        const handleUpload = () => {
            if (this.state.file) {
                this.props.uploadFile("pokojnici", "slika", "", this.state.file, 1);
                this.setState({ "fileChosen": false });
            } else {
                this.props.showFailureMessage("Greška", "Niste odabrali dokument/sliku za prijenos.");
            }

        };

        return (
            <form>
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
    uploadFile: uploadNewFileByEntityId,
    showSuccessMessage: insertSuccessful,
    showFailureMessage: insertUnsuccessful
})(FileUploadFormComponent);

export default FileUploadForm;
