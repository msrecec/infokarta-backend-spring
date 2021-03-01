import React, { useEffect, useState } from 'react';
import { createPlugin } from '../../utils/PluginsUtils';
import axios from '../../libs/ajax';

import TableComponent from '../../components/infokarta/Table';
import PaginationComponent from '../../components/infokarta/Pagination';

import { loadDeceased } from '../../actions/infokarta/pokojnici';
import { get } from 'lodash';

// import Api from '../api/Pokojnici'
import { connect } from 'react-redux';

const style = {
    position: "absolute",
    background: "white",
    zIndex: 1000,
    top: 50,
    left: 50,
    padding: 10
};

// TODO pocisti ovo i popravi dispatch
const Component = () => {
    // const [pokojnici, setPokojnici] = useState("Ovo su pokojnici");

    const getPokojnici = () => {
        const url = 'http://localhost:8080/mapstore/rest/config/pokojnici';
        axios.get(url).then(function(response) {
            loadDeceased(response.data);
            console.log(response);
        }).catch(function(error) {
            console.log(error);
        });
    };

    // const table = <TableComponent items={pokojnici} />;
    // const pagination = <PaginationComponent numberOfPages={pokojnici.length/30} />;
    return (
        <div style={style}>
            <button onClick={getPokojnici}>Dohvati pokojnike</button>
            {/* <div>{table}</div>
            <div>{pagination}</div> */}
        </div>
    );
};

const ConnectedComponent = connect((state) => {
    return {
        pokojnici: get(state, 'pokojnici.deceased')
    };
}, {
    // onGetGraves: getGraves
})(Component);

export default createPlugin('Grobovi', {
    component: ConnectedComponent
});
