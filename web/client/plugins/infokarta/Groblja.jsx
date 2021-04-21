import React from "react";
import { connect } from "react-redux";
import { get } from "lodash";

import Message from "../../components/I18N/Message";
import { Glyphicon, Button } from "react-bootstrap";

// actions
import {
    setSearchParametersForGraves,
    resetSearchParametersForGraves,
    zoomToGraveFromGraves,
    setPageForGraves
} from "../../actions/infokarta/graves";

import {
    setSearchParametersForDeceased,
    resetSearchParametersForDeceased,
    // editDeceased,
    insertDeceased,
    zoomToGraveFromDeceased,
    setPageForDeceased
} from "../../actions/infokarta/deceased";

import {
    enableGravePickMode
} from "../../actions/infokarta/gravePickerTool";

import {
    getDataForDetailsView,
    clearDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

import {
    getItemForEditFromDatabase,
    zoomToActivePluginSegment
} from "../../actions/infokarta/dynamicComponents";

// utils
import { createPlugin } from "../../utils/PluginsUtils";
import { displayFeatureInfo } from "../../utils/infokarta/ComponentConstructorUtil";

// reducers
import graves from "../../reducers/infokarta/graves";
import deceased from "../../reducers/infokarta/deceased";
import dynamicComponents from "../../reducers/infokarta/dynamicComponents";
import gravePickerTool from "../../reducers/infokarta/gravePickerTool";
import detailsAndDocuments from "../../reducers/infokarta/detailsAndDocuments";
import fileManagement from "../../reducers/infokarta/fileManagement";

// epics
import { deceasedAndGravesEpic } from "../../epics/infokarta/combinedEpics";

// components
import TableComponent from "../../components/infokarta/Table";
import EditModal from "../../components/infokarta/EditModal";
import InsertModal from "../../components/infokarta/InsertModal";
import InsertConfirmationModal from "../../components/infokarta/InsertConfirmationModal";
import SearchComponent from "../../components/infokarta/SearchForm";
import PaginationComponent from "../../components/infokarta/Pagination";
import GravePickerModal from '../../components/infokarta/pokojnici/GravePickerModal';
import PokojniciDetails from '../../components/infokarta/detailsViews/PokojniciDetails';
import GroboviDetails from "../../components/infokarta/detailsViews/GroboviDetails";
import PluginNameEmitter from '../../components/infokarta/PluginNameEmitter';

const fieldsToIncludeGrobovi = ["grobnica", "redniBroj", "groblje"];
const fieldsToIncludePokojnici = ["ime", "prezime", "datum_rodjenja", "datum_smrti"];
const fieldsToExcludeGrobovi = ["fid", "source", "source1", "source2", "source3", "source4", "source5", "source6", "source7", "fk", "ime_i_prezime"];
const fieldsToExcludePokojnici = ["fid", "fk", "ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
const readOnlyFields = ["fid", "fk", "groblje", "oznaka_grobnice"];
const searchFormDataGrobovi = [
    {
        label: "Groblje",
        type: "select",
        value: "groblje",
        selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
    }
];
const searchFormDataPokojnici = [
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

let deceasedMode = true;

const Groblja = ({
    gravesData,
    gravesPage,
    gravesTotalNumber,

    deceasedData,
    deceasedPage,
    deceasedTotalNumber,

    showGravesDetails,
    showDeceasedDetails,

    gravesDetailViewItems,
    deceasedDetailViewItems,

    gravesEditModalShow,
    deceasedEditModalShow,

    deceasedInsertModalShow,
    deceasedInsertConfirmationModalShow,

    chosenGrave,

    setGravesSearchParameters = () => {},
    resetGravesSearchParameters = () => {},
    sendGravesPageNumber = () => {},

    setDeceasedSearchParameters = () => {},
    resetDeceasedSearchParameters = () => {},
    sendDeceasedPageNumber = () => {},

    setupEditModal = () => {},
    sendEditedData = () => {},
    sendNewData = () => {},
    setupInsertModal = () => {},
    sendGraveZoomData = () => {},
    sendDeceasedZoomData = () => {},
    startChooseMode = () => {},
    sendDataToDetailsView = () => {},
    closeDetailsView = () => {}
}) => {
    const switchMode = () => {
        deceasedMode = !deceasedMode;
        if (deceasedMode) {
            setDeceasedSearchParameters({});
        } else {
            setGravesSearchParameters({});
        }
    };

    const switchDetailsViews = (fid) => {
        deceasedMode = !deceasedMode;
        sendDataToDetailsView(fid, deceasedMode ? "pokojnici" : "grobovi");
    };

    /* --------------------------- custom komponente za pokojnike --------------------------- */
    const gravePickerButtonStyle = {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    };

    const insertModalGravePickerModeButton = (
        <div style={gravePickerButtonStyle}>
            <Button bsStyle="success" onClick={() => startChooseMode()} >Odaberite grobnicu klikom na kartu</Button>
        </div>
    );

    const graveConfirmationForm = (
        <div>
            <h3>Odabrana grobnica</h3>
            {chosenGrave ? displayFeatureInfo(chosenGrave) : <span>Nije odabrana grobnica.</span>}
            <hr/>
            <h3>Pokojnikovi podaci</h3>
        </div>
    );
    /* ------------------------------- kraj custom komponenti ------------------------------- */

    const search = (<SearchComponent
        buildData={deceasedMode ? searchFormDataPokojnici : searchFormDataGrobovi}
        search={deceasedMode ? setDeceasedSearchParameters : setGravesSearchParameters}
        openInsertForm={setupInsertModal}
        resetSearchParameters={deceasedMode ? resetDeceasedSearchParameters : resetGravesSearchParameters}
        disableInsert={!deceasedMode}
    />);

    const deceasedTable = (<TableComponent
        items={deceasedData}
        fieldsToInclude={fieldsToIncludePokojnici}
        sendDataToDetailsView={sendDataToDetailsView}
        showDetails={showDeceasedDetails}
        zoomToItem={sendDeceasedZoomData}
    />);

    const gravesTable = (<TableComponent
        items={gravesData}
        fieldsToInclude={fieldsToIncludeGrobovi}
        sendDataToDetailsView={sendDataToDetailsView}
        showDetails={showGravesDetails}
        zoomToItem={sendGraveZoomData}
    />);

    const pagination = (<PaginationComponent
        totalNumber={deceasedMode ? deceasedTotalNumber : gravesTotalNumber}
        setPageNumber={deceasedMode ? sendDeceasedPageNumber : sendGravesPageNumber}
        active={deceasedMode ? deceasedPage : gravesPage}
    />);

    const gravesDetailsAndDocs = (<GroboviDetails
        items={gravesDetailViewItems}
        showDetails={showGravesDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        fieldsToExclude={fieldsToExcludeGrobovi}
        toggleDetailViews={switchDetailsViews}
    />);

    const deceasedDetailsAndDocs = (<PokojniciDetails
        items={deceasedDetailViewItems}
        showDetails={showDeceasedDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        fieldsToExclude={fieldsToExcludePokojnici}
        toggleDetailViews={switchDetailsViews}
    />);

    const gravesEditModal = (<EditModal
        fieldsToExclude={fieldsToExcludeGrobovi}
        readOnlyFields={readOnlyFields}
        editItem={sendEditedData}
        show={gravesEditModalShow}
    />);

    const deceasedEditModal = (<EditModal
        fieldsToExclude={fieldsToExcludePokojnici}
        readOnlyFields={readOnlyFields}
        editItem={sendEditedData}
        show={deceasedEditModalShow}
    />);

    const insertModal = (<InsertModal
        fieldsToExclude={fieldsToExcludePokojnici}
        extraForm={insertModalGravePickerModeButton}
        show={deceasedInsertModalShow}
    />);

    const insertConfirmationModal = (<InsertConfirmationModal
        fieldsToExclude={fieldsToExcludePokojnici}
        extraForm={graveConfirmationForm}
        insertItem={sendNewData}
        show={deceasedInsertConfirmationModalShow}
    />);

    const gravePickerModal = (<GravePickerModal
    />);

    const pluginNameEmitter = (<PluginNameEmitter
        pluginName={deceasedMode ? "pokojnici" : "grobovi"}
    />);

    const styles = {
        showGraveDetailsStyle: {
            height: "0px",
            display: "none"
        },
        hideGraveDetailsStyle: {
            height: "600px",
            transition: "all .2s linear"
        },
        showDeceasedDetailsStyle: {
            height: "150px",
            transition: "all .2s linear"
        },
        hideDeceasedDetailsStyle: {
            height: "600px",
            transition: "all .2s linear"
        }
    };

    return (
        <div style={{"padding": "10px"}}>
            {pluginNameEmitter}
            <Button bsStyle="primary" onClick={() => switchMode()}>{deceasedMode ? "Grobnica" : "Pokojnik"}</Button>
            {deceasedMode ? (
                <div>
                    {search}
                    <div style={showDeceasedDetails ? styles.showDeceasedDetailsStyle : styles.hideDeceasedDetailsStyle}>
                        {deceasedTable}
                    </div>
                    {pagination}
                    {deceasedDetailsAndDocs}
                </div>
            ) : (
                <div>
                    <div style={showGravesDetails ? styles.showGraveDetailsStyle : styles.hideGraveDetailsStyle}>
                        {search}
                        {gravesTable}
                        {pagination}
                    </div>
                    {gravesDetailsAndDocs}
                </div>
            )}
            {gravesEditModal}
            {deceasedEditModal}
            {insertModal}
            {insertConfirmationModal}
            {gravePickerModal}
        </div>
    );
};

export default createPlugin("Groblja", {
    component: connect((state) => ({
        gravesData: get(state, "graves.data"),
        gravesPage: get(state, "graves.pageNumber"),
        gravesTotalNumber: get(state, "graves.totalNumber"),

        deceasedData: get(state, "deceased.data"),
        deceasedPage: get(state, "deceased.pageNumber"),
        deceasedTotalNumber: get(state, "deceased.totalNumber"),

        showGravesDetails: get(state, "detailsAndDocuments.groboviShow"),
        showDeceasedDetails: get(state, "detailsAndDocuments.pokojniciShow"),

        gravesDetailViewItems: get(state, "detailsAndDocuments.groboviItem"),
        deceasedDetailViewItems: get(state, "detailsAndDocuments.pokojniciItem"),

        gravesEditModalShow: get(state, "dynamicComponents.modals.groboviEdit"),
        deceasedEditModalShow: get(state, "dynamicComponents.modals.pokojniciEdit"),
        deceasedInsertModalShow: get(state, "dynamicComponents.modals.pokojniciInsert"),
        deceasedInsertConfirmationModalShow: get(state, "dynamicComponents.modals.pokojniciConfirmation"),

        chosenGrave: get(state, "gravePickerTool.graveData")
    }), {
        setGravesSearchParameters: setSearchParametersForGraves,
        resetGravesSearchParameters: resetSearchParametersForGraves,
        sendGravesPageNumber: setPageForGraves,

        setDeceasedSearchParameters: setSearchParametersForDeceased,
        resetDeceasedSearchParameters: resetSearchParametersForDeceased,
        sendDeceasedPageNumber: setPageForDeceased,

        setupEditModal: getItemForEditFromDatabase,

        sendGraveZoomData: zoomToActivePluginSegment,
        sendDeceasedZoomData: zoomToActivePluginSegment,

        sendDataToDetailsView: getDataForDetailsView,
        closeDetailsView: clearDetailsAndDocsView,

        startChooseMode: enableGravePickMode,
        sendNewData: insertDeceased
    })(Groblja),
    containers: {
        DrawerMenu: {
            name: "Groblja",
            position: 2,
            text: <Message msgId="Groblja"/>,
            icon: <Glyphicon glyph="user"/>,
            action: () => ({type: ""}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: deceasedAndGravesEpic,
    reducers: {
        deceased,
        graves,
        gravePickerTool,
        dynamicComponents,
        detailsAndDocuments,
        fileManagement
    }
});
