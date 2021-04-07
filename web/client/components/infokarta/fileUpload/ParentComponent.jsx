import React from 'react';
import PropTypes from 'prop-types';

import FileUploadForm from './FileUploadForm';
import FileList from './FileList';

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
                {fileUpload}
                {fileList}
            </div>
        );
    }
}

export default ParentComponent;
