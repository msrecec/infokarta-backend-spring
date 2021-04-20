import React from 'react';
import PropTypes from 'prop-types';

import FileUploadForm from './FileUploadForm';
import FileList from './FileList';

class ParentComponent extends React.Component {
    static propTypes = {
        itemId: PropTypes.number,
        files: PropTypes.array
    };

    static defaultProps = {
        files: []
    };


    render() {
        const fileUpload = (<FileUploadForm
            itemId={this.props.itemId}
        />);

        const fileList = (<FileList
            itemId={this.props.itemId}
        />);
        return (
            <div>
                {fileUpload}
                <hr />
                {fileList}
            </div>
        );
    }
}

export default ParentComponent;
