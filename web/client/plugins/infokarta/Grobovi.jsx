import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon, Button, ControlLabel } from 'react-bootstrap';

// actions
import {

} from "../../actions/infokarta/graves";

import {
    showEditModal,
    showInsertModal
} from "../../actions/infokarta/dynamicModalControl";

import {
    loadDataIntoDetailsAndDocsView,
    closeDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

// utils
import { createPlugin } from '../../utils/PluginsUtils';
import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

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
// import InsertModal from '../../components/infokarta/InsertModal';
// import InsertConfirmationModal from '../../components/infokarta/InsertConfirmationModal';
// import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";
// import GravePickerModal from '../../components/infokarta/pokojnici/GravePickerModal';
import DetailsAndDocumentsView from '../../components/infokarta/DetailsAndDocumentsView';

const fieldsToInclude = ["ime", "prezime", "datum_rodjenja", "datum_smrti"];
const fieldsToExclude = ["fid", "fk", "ime_i_prezime", "IME I PREZIME"];
// const fieldsToExcludeInsert = ["fid", "fk", "ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
const readOnlyFields = ["fid", "fk", "groblje", "oznaka_grobnice"];
// const searchFormData = [
//     {
//         label: "Ime",
//         type: "text",
//         value: "ime"
//     },
//     {
//         label: "Prezime",
//         type: "text",
//         value: "prezime"
//     },
//     {
//         label: "Godina smrti",
//         type: "text",
//         value: "godina_ukopa"
//     },
//     {
//         label: "Groblje",
//         type: "select",
//         value: "groblje",
//         selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
//     }
// ];

const Grobovi = ({
    data,
    page,
    totalNumber,
    tableHeight,
    detailViewItem,
    showDetails,
    // sendSearchParameters = () => {},
    // resetSearchParameters = () => {},
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

    // const search = (<SearchComponent
    //     buildData={searchFormData}
    //     search={sendSearchParameters}
    //     openInsertForm={setupInsertModal}
    //     resetSearchParameters={resetSearchParameters}
    // />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToInclude={fieldsToInclude ? fieldsToInclude : []}
        sendDataToDetailsView={sendDataToDetailsView}
        tableHeight={tableHeight}
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

    // const insertModal = (<InsertModal
    //     fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
    //     extraForm={insertModalGravePickerModeButton}
    // />);

    // const insertConfirmationModal = (<InsertConfirmationModal
    //     fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
    //     extraForm={graveConfirmationForm}
    //     insertItem={sendNewData}
    //     startChooseGraveMode={startChooseMode}
    // />);

    const detailsAndDocs = (<DetailsAndDocumentsView
        item={detailViewItem}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        title={"Grobnica"}
        fieldsToExclude={fieldsToExclude}
    />);

    return (
        <div style={{"padding": "10px"}}>
            {/* {search} */}
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
        // sendSearchParameters: setSearchParametersForDeceased,
        // resetSearchParameters: resetSearchParametersForDeceased,
        // sendPageNumber: setPageForDeceased,
        setupEditModal: showEditModal,
        // sendEditedData: editDeceased,
        setupInsertModal: showInsertModal,
        // sendNewData: insertDeceased,
        // sendZoomData: zoomToGraveFromDeceased,
        // startChooseMode: enableGravePickMode,
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
