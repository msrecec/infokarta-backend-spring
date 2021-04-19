import React from "react";
import { connect } from "react-redux";
import { get } from "lodash";

import Message from "../../components/I18N/Message";
import { Glyphicon } from "react-bootstrap";

// actions
import {
    setSearchParametersForGraves,
    resetSearchParametersForGraves,
    zoomToGraveFromGraves
} from "../../actions/infokarta/graves";

import {
    getDataForDetailsView,
    clearDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

import {
    getItemForEditFromDatabase
} from "../../actions/infokarta/dynamicComponents";


// utils
import { createPlugin } from "../../utils/PluginsUtils";
// import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import graves from "../../reducers/infokarta/graves";
import dynamicComponents from "../../reducers/infokarta/dynamicComponents";
import detailsAndDocuments from "../../reducers/infokarta/detailsAndDocuments";
// import fileManagement from "../../reducers/infokarta/fileManagement";

// epics
import { completeGravesEpic } from "../../epics/infokarta/combinedEpics";

// components
import TableComponent from "../../components/infokarta/Table";
import EditModal from "../../components/infokarta/EditModal";
import SearchComponent from "../../components/infokarta/SearchForm";
import PaginationComponent from "../../components/infokarta/Pagination";
import GroboviDetails from "../../components/infokarta/GroboviDetails";
import PluginNameEmitter from '../../components/infokarta/PluginNameEmitter';

const fieldsToInclude = ["grobnica", "redniBroj", "groblje"];
const fieldsToExclude = ["fid", "source", "source1", "source2", "source3", "source4", "source5", "source6", "source7", "fk", "ime_i_prezime"];
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
    detailViewItems,
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

    const detailsAndDocs = (<GroboviDetails
        items={detailViewItems}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
    />);

    const pluginNameEmitter = (<PluginNameEmitter
        pluginName={"grobovi"}
    />);

    const showDetailsStyle = {
        height: "0px",
        display: "none"
    };

    const hideDetailsStyle = {
        height: "600px",
        transition: "all .2s linear"
    };

    return (
        <div style={{"padding": "10px"}}>
            {pluginNameEmitter}
            <div style={showDetails ? showDetailsStyle : hideDetailsStyle}>
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

export default createPlugin("Grobovi", {
    component: connect((state) => ({
        data: get(state, "graves.data"),
        page: get(state, "graves.pageNumber"),
        totalNumber: get(state, "graves.totalNumber"),
        showDetails: get(state, "detailsAndDocuments.showDetails"),
        detailViewItems: get(state, "detailsAndDocuments.items"),
        editModalShow: get(state, "dynamicComponents.modals.groboviEdit")
    }), {
        sendSearchParameters: setSearchParametersForGraves,
        resetSearchParameters: resetSearchParametersForGraves,
        // sendPageNumber: setPageForDeceased,
        setupEditModal: getItemForEditFromDatabase,
        // sendEditedData: editDeceased,
        sendZoomData: zoomToGraveFromGraves,
        sendDataToDetailsView: getDataForDetailsView,
        closeDetailsView: clearDetailsAndDocsView
    })(Grobovi),
    containers: {
        DrawerMenu: {
            name: "Grobovi",
            position: 3,
            text: <Message msgId="grobovi"/>,
            icon: <Glyphicon glyph="stop"/>,
            action: () => ({type: ""}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: completeGravesEpic,
    reducers: {
        graves,
        dynamicComponents,
        detailsAndDocuments
        // fileManagement
    }
});
