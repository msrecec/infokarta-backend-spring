import React from 'react';
import PropTypes from 'prop-types';

import FileUploadForm from './FileUploadForm';
import FileList from './FileList';

// const style = {
//     display: "flex",
//     flexDirection: "column",
//     alignItems: "center"
// };

class ParentComponent extends React.Component {
    static propTypes = {
        files: PropTypes.array
    };

    static defaultProps = {
        files: []
    };

    render() {
        const fileUpload = (<FileUploadForm
        />);

        const fileList = (<FileList
        />);

        return (
            <div>
                <div>
                    <h2>Datoteke vezane uz ovu stavku</h2>
                    <hr />
                </div>
                {fileUpload}
                <hr />
                {fileList}
            </div>
        );
    }
}

export default ParentComponent;
