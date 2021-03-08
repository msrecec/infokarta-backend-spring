import React from "react";
import { connect } from "react-redux";
import { get } from "lodash";

import {
    loadDeceased,
    loadDeceasedByPage
} from "../../actions/infokarta/pokojnici";

import { createPlugin } from "../../utils/PluginsUtils";

import pokojnici from "../../reducers/infokarta/pokojnici";
import * as epics from "../../epics/infokarta/pokojnici";

import TableComponent from "../../components/infokarta/Table";
import PaginationComponent from "../../components/infokarta/Pagination";

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
    padding: 10
};

const PokojniciPlugin = ({ data, loadDataByPage = () => {} }) => {
    const table = <TableComponent items={data ? data : []} />;
    const pagination = <PaginationComponent numberOfPages={21} sendData={loadDataByPage} />;

    return (
        <div style={style}>
            <button onClick={() => loadDataByPage(1)}>Dohvati pokojnike</button>
            {table}
            {pagination}
        </div>
    );
};

export default createPlugin("Pokojnici", {
    component: connect(
        (state) => ({
            data: get(state, "pokojnici.deceased")
        }),
        {
            loadData: loadDeceased,
            loadDataByPage: loadDeceasedByPage
        }
    )(PokojniciPlugin),
    epics,
    reducers: {
        pokojnici
    }
});
