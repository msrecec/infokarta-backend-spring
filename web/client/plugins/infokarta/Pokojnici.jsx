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
    showDynamicModal,
    getColumnsForInsertFromDatabase
} from "../../actions/infokarta/dynamicModalControl";

import {
    loadDataIntoDetailsAndDocsView,
    closeDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

// utils
import { createPlugin } from '../../utils/PluginsUtils';
import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import deceased from '../../reducers/infokarta/deceased';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import gravePickerTool from '../../reducers/infokarta/gravePickerTool';
import detailsAndDocuments from '../../reducers/infokarta/detailsAndDocuments';
import fileManagement from '../../reducers/infokarta/fileManagement';

// epics
import { completeDeceasedEpic } from '../../epics/infokarta/combinedEpics';

// components
import TableComponent from '../../components/infokarta/Table';
import EditModal from '../../components/infokarta/EditModal';
import InsertModal from '../../components/infokarta/InsertModal';
import InsertConfirmationModal from '../../components/infokarta/InsertConfirmationModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";
import GravePickerModal from '../../components/infokarta/pokojnici/GravePickerModal';
import DetailsAndDocumentsView from '../../components/infokarta/DetailsAndDocumentsView';

const fieldsToInclude = ["ime", "prezime", "datum_rodjenja", "datum_smrti"];
const insertModalName = "pokojniciInsert";
const insertConfirmationModalName = "pokojniciConfirmation";
const editModalName = "pokojniciEdit";
const fieldsToExclude = ["fid", "fk", "ime_i_prezime", "IME I PREZIME"];
const fieldsToExcludeInsert = ["fid", "fk", "ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
const readOnlyFields = ["fid", "fk", "groblje", "oznaka_grobnice"];
const searchFormData = [
    {
        label: "Ime",
        type: "text",
        value: "ime"
    },
    {
        label: "Prezime",
        type: "text",
        value: "prezime"
    },
    {
        label: "Godina smrti",
        type: "text",
        value: "godina_ukopa"
    },
    {
        label: "Groblje",
        type: "select",
        value: "groblje",
        selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
    }
];


const Pokojnici = ({
    data,
    page,
    totalNumber,
    chosenGrave,
    tableHeight,
    detailViewItem,
    showDetails,
    editModalShow,
    insertConfirmationModalShow,
    insertModalShow,
    sendSearchParameters = () => {},
    resetSearchParameters = () => {},
    sendPageNumber = () => {},
    setupEditModal = () => {},
    sendEditedData = () => {},
    sendNewData = () => {},
    setupInsertModal = () => {},
    sendZoomData = () => {},
    startChooseMode = () => {},
    sendDataToDetailsView = () => {},
    closeDetailsView = () => {}
}) => {
    // custom komponente
    const gravePickerButtonStyle = {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    };
    const insertModalGravePickerModeButton = (<div style={gravePickerButtonStyle}>
        <Button bsStyle="success" onClick={() => startChooseMode()} >Odaberite grobnicu klikom na kartu</Button>
    </div>);

    const graveConfirmationForm = (<div>
        <h3>Odabrana grobnica</h3>
        {chosenGrave ? displayFeatureInfo(chosenGrave) : <ControlLabel>Nije odabrana grobnica.</ControlLabel>}
        <hr/>
        <h3>Pokojnikovi podaci</h3>
    </div>
    );
    // kraj custom komponenti

    const search = (<SearchComponent
        buildData={searchFormData}
        search={sendSearchParameters}
        openInsertForm={setupInsertModal}
        resetSearchParameters={resetSearchParameters}
        insertModalName = {insertModalName}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        tableHeight={tableHeight}
        fieldsToInclude={fieldsToInclude ? fieldsToInclude : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        editModalName = {editModalName}
        zoomToItem={sendZoomData}
        sendDataToEdit={setupEditModal}
        sendDataToDetailsView={sendDataToDetailsView}
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
        show = {editModalShow}
    />);

    const insertModal = (<InsertModal
        fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
        extraForm={insertModalGravePickerModeButton}
        insertModalName = {insertModalName}
        insertConfirmationModalName={insertConfirmationModalName}
        show={insertModalShow}
    />);

    const insertConfirmationModal = (<InsertConfirmationModal
        fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
        extraForm={graveConfirmationForm}
        insertItem={sendNewData}
        startChooseGraveMode={startChooseMode}
        insertModalName = {insertModalName}
        insertConfirmationModalName={insertConfirmationModalName}
        show={insertConfirmationModalShow}
    />);

    const gravePickerModal = (<GravePickerModal
    />);

    const detailsAndDocs = (<DetailsAndDocumentsView
        item={detailViewItem}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        title={"Umrla osoba"}
        fieldsToExclude={fieldsToExclude}
    />);

    return (
        <div style={{"padding": "10px"}}>
            {search}
            {table}
            {pagination}
            {detailsAndDocs}
            {editModal}
            {insertModal}
            {insertConfirmationModal}
            {gravePickerModal}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "deceased.data"),
        page: get(state, "deceased.pageNumber"),
        totalNumber: get(state, "deceased.totalNumber"),
        chosenGrave: get(state, "gravePickerTool.graveData"),
        tableHeight: get(state, "detailsAndDocuments.tableHeight"),
        detailViewItem: get(state, "detailsAndDocuments.item"),
        showDetails: get(state, "detailsAndDocuments.showDetails"),
        editModalShow: get(state, 'dynamicModalControl.modals.' + editModalName),
        insertModalShow: get(state, 'dynamicModalControl.modals.' + insertModalName),
        insertConfirmationModalShow: get(state, 'dynamicModalControl.modals.' + insertConfirmationModalName)
    }), {
        sendSearchParameters: setSearchParametersForDeceased,
        resetSearchParameters: resetSearchParametersForDeceased,
        sendPageNumber: setPageForDeceased,
        setupEditModal: showDynamicModal,
        setupInsertModal: getColumnsForInsertFromDatabase,
        sendEditedData: editDeceased,
        sendNewData: insertDeceased,
        sendZoomData: zoomToGraveFromDeceased,
        startChooseMode: enableGravePickMode,
        sendDataToDetailsView: loadDataIntoDetailsAndDocsView,
        closeDetailsView: closeDetailsAndDocsView
    })(Pokojnici),
    containers: {
        DrawerMenu: {
            name: "Pokojnici",
            position: 2,
            text: <Message msgId="pokojnici"/>,
            icon: <Glyphicon glyph="user"/>,
            action: () => ({type: ''}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: completeDeceasedEpic,
    reducers: {
        deceased,
        dynamicModalControl,
        gravePickerTool,
        detailsAndDocuments,
        fileManagement
    }
});
