import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon } from 'react-bootstrap';
// import layersIcon from '../toolbar/assets/img/layers.png';

import {
    loadDeceased,
    editDeceased,
    insertDeceased,
    zoomToGrave,
    enableGravePickMode
} from "../../actions/infokarta/pokojnici";

import {
    showEditModal,
    showInsertModal
} from "../../actions/infokarta/dynamicModalControl";

import {
    setPaginationNumber
} from "../../actions/infokarta/paginationControl";

import { createPlugin } from '../../utils/PluginsUtils';

import pokojnici from '../../reducers/infokarta/pokojnici';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import paginationControl from '../../reducers/infokarta/paginationControl';
import GravePickerModal from '../../components/infokarta/GravePickerModal';

import * as epics from '../../epics/infokarta/pokojnici';

import TableComponent from '../../components/infokarta/Table';
import EditModal from '../../components/infokarta/EditModal';
import InsertModal from '../../components/infokarta/InsertModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";

const style = {
    padding: 10
};

const fieldsToExclude = ["ime_i_prezime", "IME I PREZIME", "groblje", "oznaka_grobnice"];
const readOnlyFields = ["fid", "fk"];

const Pokojnici = ({
    data,
    page,
    totalNumber,
    loadDeceasedData = () => {},
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

    // const insertFormHeaders = [
    //     {
    //         section: 1,
    //         title: "Odabir grobnice"
    //     },
    //     {
    //         section: 2,
    //         title: "Unos pokojnikovih podataka"
    //     },
    //     {
    //         section: 3,
    //         title: "Prijenos datoteka"
    //     }
    // ];
    // TODO smislit nacin za dodat ove naslove u sekcije dinamicki

    const insertFormData = [
        {
            label: "Odaberite grobnicu klikom na kartu",
            type: "button",
            bsStyle: "success",
            onClickfunction: startChooseMode
        }
    ];

    const search = (<SearchComponent
        buildData={searchFormData}
        search={loadDeceasedData}
        pageNumber={typeof page === "number" ? page : 1}
        openInsertForm={setupInsertModal}
        resetPagination={sendPageNumber}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        sendDataToEdit={setupEditModal}
        zoomToItem={sendZoomData}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        sendPageNumber={sendPageNumber}
        active={typeof page === "number" ? page : 1}
    />);

    const editModal = (<EditModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        editItem={sendEditedData}
    />);

    const insertModal = (<InsertModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        extraForm={insertFormData}
        insertItem={sendNewData}
        startChooseGraveMode={startChooseMode}
    />);

    const gravePickerModal = (<GravePickerModal
    />);

    return (
        <div style={style}>
            {search}
            {table}
            {pagination}
            {editModal}
            {insertModal}
            {gravePickerModal}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "pokojnici.deceased"),
        page: get(state, "paginationControl.pageNumber"),
        totalNumber: get(state, "pokojnici.totalNumber")
    }), {
        loadDeceasedData: loadDeceased,
        sendPageNumber: setPaginationNumber,
        setupEditModal: showEditModal,
        setupInsertModal: showInsertModal,
        sendEditedData: editDeceased,
        sendNewData: insertDeceased,
        sendZoomData: zoomToGrave,
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
    epics,
    reducers: {
        pokojnici,
        dynamicModalControl,
        paginationControl
    }
});
