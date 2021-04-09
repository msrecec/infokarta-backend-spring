import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon, Button, ControlLabel } from 'react-bootstrap';

// actions

// utils
import { createPlugin } from '../../utils/PluginsUtils';
import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import fileManagement from '../../reducers/infokarta/fileManagement';
import detailsAndDocuments from '../../reducers/infokarta/detailsAndDocuments';

// epics
import * as epics from '../../epics/infokarta/fileManagement';

// components
import FileComponentParent from '../../components/infokarta/fileUpload/ParentComponent';

const style = {
    padding: 16,
    overflow: "auto",
    maxHeight: "800px",
    minWidth: "580px"
};

const DetailsAndDocumentsPlugin = ({
    item
}) => {

    const fileComponentParent = (<FileComponentParent
        itemId={item.fid ? item.fid : null}
    />);

    return (
        <div style={style}>
            <h2>Detalji stavke</h2>
            {displayFeatureInfo(item)}
            <hr />
            {fileComponentParent}
        </div>
    );
};

export default createPlugin('DetailsAndDocuments', {
    component: connect((state) => ({
        item: get(state, "detailsAndDocuments.item")
    }), {
    })(DetailsAndDocumentsPlugin),
    containers: {
        DrawerMenu: {
            name: "DetailsAndDocuments",
            position: 100,
            text: <Message msgId="detailsAndDocuments"/>,
            icon: <Glyphicon glyph="eye-open"/>,
            action: () => ({type: ''}),
            priority: 1,
            doNotHide: true
        }
    },
    epics,
    reducers: {
        fileManagement,
        detailsAndDocuments
    }
});
