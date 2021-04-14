import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon, Button, ControlLabel } from 'react-bootstrap';

// actions
import {
    setSearchParametersForGraves,
    resetSearchParametersForGraves
} from "../../actions/infokarta/graves";

import {
    showDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

import {
    loadDataIntoDetailsAndDocsView,
    closeDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

// utils
import { createPlugin } from '../../utils/PluginsUtils';
// import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import graves from '../../reducers/infokarta/graves';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import detailsAndDocuments from '../../reducers/infokarta/detailsAndDocuments';
import fileManagement from '../../reducers/infokarta/fileManagement';

// epics
import { completeGravesEpic } from '../../epics/infokarta/combinedEpics';

// components
import TableComponent from '../../components/infokarta/Table';
import EditModal from '../../components/infokarta/EditModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";
import DetailsAndDocumentsView from '../../components/infokarta/DetailsAndDocumentsView';

// modal names
const editModalName = "groboviEdit";

const fieldsToInclude = ["fid", "brojLezaja", "grobnica", "redniBroj", "groblje"];
const fieldsToExclude = [];
const readOnlyFields = [];
const searchFormData = [
    {
        label: "Groblje",
        type: "select",
        value: "groblje",
        selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
    }
];

const Grobovi = ({
    data,
    page,
    totalNumber,
    tableHeight,
    detailViewItem,
    showDetails,
    sendSearchParameters = () => {},
    resetSearchParameters = () => {},
    sendPageNumber = () => {},
    setupEditModal = () => {},
    sendEditedData = () => {},
    // sendNewData = () => {},
    // setupInsertModal = () => {},
    sendZoomData = () => {},
    // startChooseMode = () => {},
    sendDataToDetailsView = () => {},
    closeDetailsView = () => {}
}) => {

    const search = (<SearchComponent
        buildData={searchFormData}
        search={sendSearchParameters}
        // openInsertForm={setupInsertModal}
        resetSearchParameters={resetSearchParameters}
        disableInsert
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToInclude={fieldsToInclude ? fieldsToInclude : []}
        sendDataToDetailsView={sendDataToDetailsView}
        tableHeight={tableHeight}
        editModalName={editModalName}
        zoomToItem={sendZoomData}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        setPageNumber={sendPageNumber}
        active={typeof page === "number" ? page : 1}
    />);

    const editModal = (<EditModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        editItem={sendEditedData}
    />);

    const detailsAndDocs = (<DetailsAndDocumentsView
        item={detailViewItem}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        title={"Grobnica"}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
    />);

    return (
        <div style={{"padding": "10px"}}>
            {search}
            {table}
            {pagination}
            {detailsAndDocs}
            {editModal}
            {/* {insertModal}
            {insertConfirmationModal} */}
        </div>
    );
};

export default createPlugin('Grobovi', {
    component: connect((state) => ({
        data: get(state, "graves.data"),
        page: get(state, "graves.pageNumber"),
        totalNumber: get(state, "graves.totalNumber"),
        tableHeight: get(state, "detailsAndDocuments.tableHeight"),
        detailViewItem: get(state, "detailsAndDocuments.item"),
        showDetails: get(state, "detailsAndDocuments.showDetails")
    }), {
        sendSearchParameters: setSearchParametersForGraves,
        resetSearchParameters: resetSearchParametersForGraves,
        // sendPageNumber: setPageForDeceased,
        setupEditModal: showDynamicModal,
        // sendEditedData: editDeceased,
        // sendZoomData: zoomToGraveFromDeceased,
        sendDataToDetailsView: loadDataIntoDetailsAndDocsView,
        closeDetailsView: closeDetailsAndDocsView
    })(Grobovi),
    containers: {
        DrawerMenu: {
            name: "Grobovi",
            position: 3,
            text: <Message msgId="grobovi"/>,
            icon: <Glyphicon glyph="stop"/>,
            action: () => ({type: ''}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: completeGravesEpic,
    reducers: {
        graves,
        dynamicModalControl,
        detailsAndDocuments,
        fileManagement
    }
});
