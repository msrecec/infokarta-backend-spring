import React from 'react';
import PropTypes from 'prop-types';
import {Button, FormControl, FormGroup, ControlLabel} from 'react-bootstrap';

class FileUploadForm extends React.Component {
    render() {
        return (
            <form>
                <FormGroup
                    key="uploadForm"
                    controlId="uploadForm"
                >
                    <ControlLabel>Prenesite novi dokument ili sliku</ControlLabel>
                    <FormControl
                        type="file"
                    />
                </FormGroup>
            </form>
        );
    }
}

export default FileUploadForm;
