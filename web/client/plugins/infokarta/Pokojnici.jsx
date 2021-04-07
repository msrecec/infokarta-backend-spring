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
    const searchFormData = [
        {
            label: "Ime",
            type: "text",
            value: "name"
        },
        {
            label: "Prezime",
            type: "text",
            value: "surname"
        },
        {
            label: "Godina smrti od",
            type: "text",
            value: "yearOfDeathFrom"
        },
        {
            label: "Godina smrti do",
            type: "text",
            value: "yearOfDeathTo"
        },
        {
            label: "Groblje",
            type: "select",
            value: "graveyard",
            selectValues: ["", "Primošten", "Prhovo", "Široke", "Kruševo"]
        }
    ];

    const search = (<SearchComponent
        buildData={searchFormData}
        search={sendSearchParameters}
        openInsertForm={setupInsertModal}
        resetSearchParameters={resetSearchParameters}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        sendDataToEdit={setupEditModal}
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

    const gravePickerButtonStyle = {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    };
    const insertModalGravePickerModeButton = (<div style={gravePickerButtonStyle}>
        <Button bsStyle="success" onClick={() => startChooseMode()} >Odaberite grobnicu klikom na kartu</Button>
    </div>);

    const insertModal = (<InsertModal
        fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
        extraForm={insertModalGravePickerModeButton}
    />);

    const graveConfirmationForm = (<div>
        <h3>Odabrana grobnica</h3>
        {chosenGrave ? displayFeatureInfo(chosenGrave) : <ControlLabel>Nije odabrana grobnica.</ControlLabel>}
        <hr/>
        <h3>Pokojnikovi podaci</h3>
    </div>
    );

    const insertConfirmationModal = (<InsertConfirmationModal
        fieldsToExclude={fieldsToExcludeInsert ? fieldsToExcludeInsert : []}
        extraForm={graveConfirmationForm}
        insertItem={sendNewData}
        startChooseGraveMode={startChooseMode}
    />);

    const gravePickerModal = (<GravePickerModal
    />);

    //
    const fileContainer = (<FileContainer/>);
    //

    return (
        <div style={style}>
            {search}
            {table}
            {pagination}
            {editModal}
            {insertModal}
            {insertConfirmationModal}
            {gravePickerModal}
            {fileContainer}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "deceased.data"),
        page: get(state, "deceased.pageNumber"),
        totalNumber: get(state, "deceased.totalNumber"),
        chosenGrave: get(state, 'gravePickerTool.graveData')
    }), {
        sendSearchParameters: setSearchParametersForDeceased,
        resetSearchParameters: resetSearchParametersForDeceased,
        sendPageNumber: setPageForDeceased,
        setupEditModal: showEditModal,
        setupInsertModal: showInsertModal,
        sendEditedData: editDeceased,
        sendNewData: insertDeceased,
        sendZoomData: zoomToGraveFromDeceased,
        startChooseMode: enableGravePickMode
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
    epics: deceasedAndFileManagementEpic,
    reducers: {
        deceased,
        dynamicModalControl,
        fileManagement,
        gravePickerTool
    }
});
