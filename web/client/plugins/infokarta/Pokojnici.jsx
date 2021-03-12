import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {
    loadDeceased,
    editDeceased
} from "../../actions/infokarta/pokojnici";

import {
    showDynamicModal
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
import ModalComponent from '../../components/infokarta/EditModal';
import SearchComponent from '../../components/infokarta/SearchForm';
import PaginationComponent from "../../components/infokarta/Pagination";

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
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
        selectValues: ["", "Primosten", "Prhovo", "Siroke", "Krusevo"] // prvi uvik triba bit prazan
    }
];

const PokojniciPlugin = ({
    data,
    page,
    totalNumber,
    fieldsToExclude,
    readOnlyFields,
    loadDeceasedData = () => {},
    getPageNumber = () => {},
    getDataToEdit = () => {},
    sendEditedData = () => {}
    // callback funkcija: dohvacanje podataka iz child komponente
    // njoj se pripiše akcija showDynamicModal i ona se onda proslijedi u child
}) => {

    const search = (<SearchComponent
        buildData={formData}
        search={loadDeceasedData}
        pageNumber={page ? page : 1}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        sendDataToEdit={getDataToEdit}
    />);

    const editModal = (<ModalComponent
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
        editItem={sendEditedData}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        sendPageNumber={getPageNumber}
    />);

    return (
        <div style={style}>
            {search}
            {table}
            {pagination}
            {editModal}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "pokojnici.deceased"),
        page: get(state, "paginationControl.pageNumber"),
        totalNumber: get(state, "pokojnici.totalNumber"),
        fieldsToExclude: get(state, "pokojnici.fieldsToExclude"),
        readOnlyFields: get(state, "pokojnici.readOnlyFields")
    }), {
        loadDeceasedData: loadDeceased,
        getPageNumber: setPaginationNumber,
        getDataToEdit: showDynamicModal,
        sendEditedData: editDeceased
    })(PokojniciPlugin),
    epics,
    reducers: {
        pokojnici,
        dynamicModalControl,
        paginationControl
    }
});
