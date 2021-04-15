import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon } from 'react-bootstrap';

// actions
import {
    setSearchParametersForGraves,
    resetSearchParametersForGraves,
    zoomToGraveFromGraves
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

const fieldsToInclude = ["grobnica", "redniBroj", "groblje"];
const fieldsToExclude = ["fid", "source", "source1", "source2", "source3"];
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
    showDetails,
    detailViewItem,
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
        showDetails={showDetails}
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
        additionalTitle={"grobnica"}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
    />);

    const showDetailsStyle = {
        height: "0px",
        display: "none"
    };

    return (
        <div style={{"padding": "10px"}}>
            <div style={showDetails ? showDetailsStyle : {}}>
                {search}
                {table}
                {pagination}
            </div>
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
        showDetails: get(state, "detailsAndDocuments.showDetails"),
        detailViewItem: get(state, "detailsAndDocuments.item")
    }), {
        sendSearchParameters: setSearchParametersForGraves,
        resetSearchParameters: resetSearchParametersForGraves,
        // sendPageNumber: setPageForDeceased,
        setupEditModal: showDynamicModal,
        // sendEditedData: editDeceased,
        sendZoomData: zoomToGraveFromGraves,
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
