import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {
    loadDeceased
} from "../../actions/infokarta/pokojnici";

import {
    showDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

import { createPlugin } from '../../utils/PluginsUtils';

import pokojnici from '../../reducers/infokarta/pokojnici';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';
import * as epics from '../../epics/infokarta/pokojnici';

import TableComponent from '../../components/infokarta/Table';
import ModalComponent from '../../components/infokarta/EditModal';
import SearchComponent from '../../components/infokarta/SearchForm';
// import PaginationComponent from '../../components/infokarta/Pagination';

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
        selectValues: ["Primosten", "Prhovo", "Siroke", "Krusevo"]
    }
];

const PokojniciPlugin = ({
    data,
    fieldsToExclude,
    readOnlyFields,
    loadDeceasedData = () => {},
    callbackGet = () => {}
    // callback funkcija: dohvacanje podataka iz child komponente
    // njoj se pripiše akcija showDynamicModal i ona se onda proslijedi u child
}) => {

    const search = (<SearchComponent
        buildData={formData}
        search={loadDeceasedData}
    />);

    const table = (<TableComponent
        items={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        sendDataToEdit={callbackGet}
    />);

    const editModal = (<ModalComponent
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
        readOnlyFields={readOnlyFields ? readOnlyFields : []}
    />);

    return (
        <div style={style}>
            {search}
            {table}
            {editModal}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "pokojnici.deceased"),
        fieldsToExclude: get(state, "pokojnici.fieldsToExclude"),
        readOnlyFields: get(state, "pokojnici.readOnlyFields")
    }), {
        loadDeceasedData: loadDeceased,
        callbackGet: showDynamicModal
    })(PokojniciPlugin),
    epics,
    reducers: {
        pokojnici,
        dynamicModalControl
    }
});
