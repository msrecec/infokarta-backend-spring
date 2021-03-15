import React from 'react';
import { connect } from 'react-redux';
import { assign, get } from 'lodash';

import Message from '../../components/I18N/Message';
import { Glyphicon } from 'react-bootstrap';
// import layersIcon from '../toolbar/assets/img/layers.png';

import {
    loadDeceased,
    editDeceased
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
import * as epics from '../../epics/infokarta/pokojnici';

import TableComponent from '../../components/infokarta/Table';
import EditModal from '../../components/infokarta/EditModal';
import InsertModal from '../../components/infokarta/InsertModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";

const style = {
    padding: 10
};

const formData = [
    {
        label: "Ime", // naziv koji ce bit dodijeljen labelu u formi
        type: "text", // tip koji ce bit dodijeljen inputu u formi
        value: "name" // value koji ce bit poslan u search funkciju, vidit u API
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
        selectValues: ["", "Primosten", "Prhovo", "Siroke", "Krusevo"] // prvi value uvijek treba bit prazan za select fieldove
    }
];

const fieldsToExclude = ["fid", "fk", "ime_i_prezime", "IME I PREZIME"];
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
    setupInsertModal = () => {}
}) => {

    const search = (<SearchComponent
        buildData={formData}
        search={loadDeceasedData}
        pageNumber={page ? page : 1}
        openInsertForm={setupInsertModal}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        sendDataToEdit={setupEditModal}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        sendPageNumber={sendPageNumber}
    />);

    const editModal = (<EditModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        editItem={sendEditedData}
    />);

    const insertModal = (<InsertModal
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        insertItem={sendNewData}
    />);

    return (
        <div style={style}>
            {search}
            {table}
            {pagination}
            {editModal}
            {insertModal}
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
        sendNewData: showInsertModal
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
