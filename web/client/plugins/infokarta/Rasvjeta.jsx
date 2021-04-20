import React from "react";
import { connect } from "react-redux";
import { get } from "lodash";

import Message from "../../components/I18N/Message";
import { Glyphicon, Button } from "react-bootstrap";

// actions
import {
    editLighting,
    getLightingData,
    setPageForLighting,
    zoomToLampFromLighting,
    setSearchParametersForLighting,
    resetSearchParametersForLighting
} from "../../actions/infokarta/lighting";

import {
    showDynamicModal
} from "../../actions/infokarta/dynamicComponents";

import {
    getDataForDetailsView,
    clearDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

// utils

// reducers
import lighting from '../../reducers/infokarta/lighting';
import dynamicComponents from "../../reducers/infokarta/dynamicComponents";
import detailsAndDocuments from "../../reducers/infokarta/detailsAndDocuments";
import fileManagement from "../../reducers/infokarta/fileManagement";

// epics
/* import * as epics from '../../epics/infokarta/lighting'; */
import { completeLightingEpics } from '../../epics/infokarta/combinedEpics';

// components
import TableComponent from "../../components/infokarta/Table";
import PaginationComponent from "../../components/infokarta/Pagination";
import { createPlugin } from '../../utils/PluginsUtils';
import EditModal from '../../components/infokarta/EditModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PluginNameEmitter from '../../components/infokarta/PluginNameEmitter';
import RasvjetaDetails from "../../components/infokarta/RasvjetaDetails";

const fieldsToInclude = ["fid", "materijal", "stanje"];
const fieldsToExclude = ["fid", "geom", "source", "mjernoMjesto",
    "vod",
    "kategorija",
    "vrstaRasvjetnogMjesta",
    "razdjelnik",
    "trosilo",
    "vrstaSvjetiljke",
    "brojSvjetiljki",
    "grlo",
    "vrstaStakla",
    "polozajKabela",
    "godinaIzgradnje",
    "idHist",
    "oznakaUgovora",
    "timeStart",
    "timeEnd",
    "userRole"];
const readOnlyFields = [];
const Rasvjeta = ({
    data,
    page,
    totalNumber,
    showDetails,
    editModalShow,
    detailViewItem,
    /* loadData = () => {}, */
    sendPageNumber = () => {},
    sendZoomData = () => {},
    setupEditModal = () => {},
    /* sendEditedData = () => {}, */
    closeDetailsView = () => {},
    sendDataToDetailsView = () => {},
    sendSearchParameters = () => {},
    resetSearchParameters = () => {}
}) => {
    const searchFormData = [
        {
            label: "Materijal",
            type: "select",
            value: "material",
            selectValues: ["", "metal", "drvo", "plastika", "beton"]
        },
        {
            label: "Stanje",
            type: "select",
            value: "state",
            selectValues: ["", "dobro", "lose"]
            // Ovih vrijednosti još nema
            // Ova komponenta neka ostane zakomentirana
            // Dok se ne unesu grla u db
        }
    ];
    const table = (<TableComponent
        items ={data ? data : []}
        fieldsToInclude={fieldsToInclude ? fieldsToInclude : []}
        sendDataToDetailsView={sendDataToDetailsView}
        zoomToItem={sendZoomData}
        showDetails={showDetails}
    />);

    const editModal = (<EditModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        /* editItem = {sendEditedData} */
        show = {editModalShow}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        setPageNumber={sendPageNumber}
        active={typeof page === "number" ? page : 1}
    />);

    const pluginNameEmitter = (<PluginNameEmitter
        pluginName={"rasvjeta"}
    />);

    const search = (<SearchComponent
        buildData={searchFormData}
        search={sendSearchParameters}
        resetSearchParameters={resetSearchParameters}
    />);
    const showDetailsStyle = {
        height: "150px",
        transition: "all .2s linear"
    };

    const hideDetailsStyle = {
        height: "600px",
        transition: "all .2s linear"
    };

    const detailsAndDocs = (<RasvjetaDetails
        items={detailViewItem}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        fieldsToExclude={fieldsToExclude}
    />);

    return (
        <div style={{"padding": "10px"}}>
            {pluginNameEmitter}
            {search}
            <div style={showDetails ? showDetailsStyle : hideDetailsStyle}>
                {table}
            </div>
            {pagination}
            {detailsAndDocs}
            {editModal}
        </div>
    );
};

export default createPlugin("Rasvjeta", {
    component: connect((state) => ({
        data: get(state, "lighting.data"),
        page: get(state, "lighting.pageNumber"),
        showLightingDetails: get(state, "detailsAndDocuments.rasvjetaShow"),
        totalNumber: get(state, "lighting.totalNumber"),
        editModalShow: get(state, "dynamicModalControl.modals.rasvjetaEdit"),
        lightingDetailViewItems: get(state, "detailsAndDocuments.rasvjetaItem")
    }), {
        loadData: getLightingData,
        sendPageNumber: setPageForLighting,
        sendZoomData: zoomToLampFromLighting,
        sendEditedData: editLighting,
        setupEditModal: showDynamicModal,
        sendSearchParameters: setSearchParametersForLighting,
        resetSearchparameters: resetSearchParametersForLighting,
        sendDataToDetailsView: getDataForDetailsView,
        closeDetailsView: clearDetailsAndDocsView
    })(Rasvjeta),
    containers: {
        DrawerMenu: {
            name: "Rasvjeta",
            position: 4,
            text: <Message msgId="rasvjeta"/>,
            icon: <Glyphicon glyph="asterisk"/>,
            action: () => ({type: ""}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: completeLightingEpics,
    reducers: {
        lighting,
        dynamicComponents,
        detailsAndDocuments,
        fileManagement
    }
});
