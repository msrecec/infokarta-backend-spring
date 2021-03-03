import React from 'react';
import { connect } from 'react-redux';
import { get } from "lodash";

import {
    loadDeceased
} from "../../actions/infokarta/pokojnici";

import { createPlugin } from '../../utils/PluginsUtils';

import pokojnici from '../../reducers/infokarta/pokojnici';
import * as epics from '../../epics/infokarta/pokojnici';

import TableComponent from '../../components/infokarta/Table';
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
    loadData = () => {}
}) => {
    const table = (<TableComponent
        items={data ? data : []} />);

    return (
        <div style={style}>
            <button onClick={loadData}>Dohvati pokojnike</button>
            {table}
        </div>
    );
};

export default createPlugin('Pokojnici', {
    component: connect((state) => ({
        data: get(state, "pokojnici.deceased")
    }), {
        loadData: loadDeceased
    })(PokojniciPlugin),
    epics,
    reducers: {
        pokojnici
    }
});
