import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import { Glyphicon, Button } from 'react-bootstrap';

import Message from '../../components/I18N/Message';

// actions
import {
    editLighting,
    getLightingData,
    setPageForLighting,
    zoomToLampFromLighting,
    setSearchParametersForLighting,
    resetSearchParametersForDeceased
} from '../../actions/infokarta/lighting';

import {
    showDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

import {
    loadDataIntoDetailsAndDocsView,
    closeDetailsAndDocsView
} from "../../actions/infokarta/detailsAndDocuments";

// utils

// reducers
import lighting from '../../reducers/infokarta/lighting';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import detailsAndDocuments from '../../reducers/infokarta/detailsAndDocuments';

// epics
/* import * as epics from '../../epics/infokarta/lighting'; */
import { completeLightingEpics } from '../../epics/infokarta/combinedEpics';

// components
import TableComponent from '../../components/infokarta/Table';
import PaginationComponent from "../../components/infokarta/Pagination";
import { createPlugin } from '../../utils/PluginsUtils';
import EditModal from '../../components/infokarta/EditModal';
import DetailsAndDocumentsView from '../../components/infokarta/DetailsAndDocumentsView';
import SearchComponent from '../../components/infokarta/SearchForm';


const style = {
    padding: 10
};
const editModalName = "rasvjetaEdit";
const fieldsToInclude = ["fid", "materijal", "stanje"];
const fieldsToExclude = ["geom", "source", "mjernoMjesto",
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
    "oznakaUgovora",
    "idHist",
    "timeStart",
    "timeEnd",
    "userRole"];
const readOnlyFields = [];
const Rasvjeta = ({
    data,
    page,
    totalNumber,
    tableHeight,
    editModalShow,
    showDetails,
    detailViewItem,
    loadData = () => {},
    sendPageNumber = () => {},
    sendZoomData = () => {},
    setupEditModal = () => {},
    sendEditedData = () => {},
    closeDetailsView = () => {},
    sendDataToDetailsView = () => {}
/*     setSearchParametersForLighting = () => {},
    resetSearchParametersForDeceased = () => {} */
}) => {
/*     const searchFormData = [
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
        },
        {
            label: "Grlo",
            type: "select",
            value: "socket",
            selectValues: [""]

            //Ovih vrijednosti još nema
            //Ova komponenta neka ostane zakomentirana
            //Dok se ne unesu grla u db
        }
    ]; */
    const table = (<TableComponent
        items ={data ? data : []}
        tableHeight={tableHeight}
        fieldsToInclude={fieldsToInclude ? fieldsToInclude : []}
        sendDataToEdit={setupEditModal}
        editModalName = {editModalName}
        zoomToItem={sendZoomData}
        sendDataToDetailsView={sendDataToDetailsView}
    />);

    const editModal = (<EditModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        editItem = {sendEditedData}
        show = {editModalShow}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        setPageNumber={sendPageNumber}
        active={typeof page === "number" ? page : 1}
    />);

    /*     const search = (<SearchComponent
        buildData={serchFormData}
        search={sendSearchParameters}
        resetSearchParameters={resetSearchParameters}
    />); */

    const detailsAndDocs = (<DetailsAndDocumentsView
        item={detailViewItem}
        showDetails={showDetails}
        closeDetailsView={closeDetailsView}
        editItem={setupEditModal}
        editModalName = {editModalName}
        title={"Rasvjetni objekt"}
        fieldsToExclude={fieldsToExclude}
    />);

    return (
        <div style = {style}>
            <Button onClick={() => loadData()}>Dohvati lampe</Button>
            {/* {search} */}
            {table}
            {pagination}
            {detailsAndDocs}
            {editModal}
        </div>
    );
};

export default createPlugin('Rasvjeta', {
    component: connect((state) => ({
        data: get(state, 'lighting.data'),
        page: get(state, 'lighting.pageNumber'),
        totalNumber: get(state, 'lighting.totalNumber'),
        editModalShow: get(state, 'dynamicModalControl.modals.' + editModalName),
        tableHeight: get(state, "detailsAndDocuments.tableHeight"),
        detailViewItem: get(state, "detailsAndDocuments.item"),
        showDetails: get(state, "detailsAndDocuments.showDetails")
    }), {
        loadData: getLightingData,
        sendPageNumber: setPageForLighting,
        sendZoomData: zoomToLampFromLighting,
        sendEditedData: editLighting,
        setupEditModal: showDynamicModal,
        sendDataToDetailsView: loadDataIntoDetailsAndDocsView,
        closeDetailsView: closeDetailsAndDocsView
        /*         sendSearchParameters: setSearchParametersForLighting,
        resetSearchparameters: resetSearchParametersForDeceased */
    })(Rasvjeta),
    containers: {
        DrawerMenu: {
            name: "Rasvjeta",
            position: 4,
            text: <Message msgId="rasvjeta"/>,
            icon: <Glyphicon glyph="asterisk"/>,
            action: () => ({type: ''}),
            priority: 1,
            doNotHide: true
        }
    },
    epics: completeLightingEpics,
    reducers: {
        lighting,
        dynamicModalControl,
        detailsAndDocuments
    }
});
