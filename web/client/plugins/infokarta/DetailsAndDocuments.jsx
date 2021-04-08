import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon, Button, ControlLabel } from 'react-bootstrap';

// actions
import {
    setSearchParametersForDeceased,
    resetSearchParametersForDeceased,
    editDeceased,
    insertDeceased,
    zoomToGraveFromDeceased,
    setPageForDeceased
} from "../../actions/infokarta/deceased";

import {
    enableGravePickMode
} from "../../actions/infokarta/gravePickerTool";

import {
    showEditModal,
    showInsertModal
} from "../../actions/infokarta/dynamicModalControl";

// utils
import { createPlugin } from '../../utils/PluginsUtils';
import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import deceased from '../../reducers/infokarta/deceased';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import gravePickerTool from '../../reducers/infokarta/gravePickerTool';
import fileManagement from '../../reducers/infokarta/fileManagement';

// epics
import { deceasedAndFileManagementEpic } from '../../epics/infokarta/combinedEpics';

// components
import TableComponent from '../../components/infokarta/Table';
import EditModal from '../../components/infokarta/EditModal';
import InsertModal from '../../components/infokarta/InsertModal';
import InsertConfirmationModal from '../../components/infokarta/InsertConfirmationModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";
import GravePickerModal from '../../components/infokarta/pokojnici/GravePickerModal';
import FileContainer from '../../components/infokarta/fileUpload/ParentComponent';

const style = {
    padding: 10
};

const fieldsToExclude = ["fid", "fk", "ime_i_prezime", "IME I PREZIME"];
const fieldsToExcludeInsert = ["fid", "fk", "ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
const readOnlyFields = ["fid", "fk", "groblje", "oznaka_grobnice"];

const Pokojnici = ({
    data,
    page,
    totalNumber,
    chosenGrave,
    sendSearchParameters = () => {},
    resetSearchParameters = () => {},
    sendPageNumber = () => {},
    setupEditModal = () => {},
    sendEditedData = () => {},
    sendNewData = () => {},
    setupInsertModal = () => {},
    sendZoomData = () => {},
    startChooseMode = () => {}
}) => {

    return (
        <div style={style}>
        </div>
    );
};

export default createPlugin('DetailsAndDocuments', {
    component: connect((state) => ({
    }), {
    })(Pokojnici),
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
    epics: {},
    reducers: {
        fileManagement
    }
});
