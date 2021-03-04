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
// import PaginationComponent from '../../components/infokarta/Pagination';

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
    padding: 10
};

const PokojniciPlugin = ({
    data,
    loadData = () => {},
    callbackGet = () => {}
    // callback funkcija: dohvacanje podataka iz child komponente
    // njoj se pripiše akcija showDynamicModal
}) => {
    const table = (<TableComponent
        items={data ? data : []} sendData={callbackGet} />);

    const editModal = (<ModalComponent />);

    return (
        <div style={style}>
            <button onClick={loadData}>Dohvati pokojnike</button>
            {table}
            {editModal}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "pokojnici.deceased")
    }), {
        loadData: loadDeceased,
        callbackGet: showDynamicModal
    })(PokojniciPlugin),
    epics,
    reducers: {
        pokojnici,
        dynamicModalControl
    }
});
