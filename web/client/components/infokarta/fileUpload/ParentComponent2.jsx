import React from 'react';
import PropTypes from 'prop-types';

import FileUploadForm from './FileUploadForm';
import FileList from './FileList';

let fileUpload = null;
let fileList = null;
class ParentComponent2 extends React.Component {
    static propTypes = {
        itemId: PropTypes.number,
        files: PropTypes.array
    };

    static defaultProps = {
        files: []
    };

    componentDidUpdate(prevProps) {
        if (prevProps.itemId !== this.props.itemId) {
            console.log("Parenta", this.props.itemId);
            fileUpload = (<FileUploadForm
                itemId={this.props.itemId}
            />);

            fileList = (<FileList
                itemId={this.props.itemId}
            />);
        }
    }
    render() {


        return (
            <div>
                {fileUpload}
                <hr />
                {fileList}
            </div>
        );
    }
}

export default ParentComponent2;
