import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import { Glyphicon, Button, ControlLabel } from 'react-bootstrap';

import Message from '../../components/I18N/Message';

// actions
import {
    getLightingData,
    setPageForLighting
} from '../../actions/infokarta/lighting';

// utils

// reducers
import lighting from '../../reducers/infokarta/lighting';

// epics
import * as epics from '../../epics/infokarta/lighting';

// components
import TableComponent from '../../components/infokarta/Table';
import PaginationComponent from "../../components/infokarta/Pagination";
import { createPlugin } from '../../utils/PluginsUtils';

const style = {
    padding: 10
};

const fieldsToExclude = ["geom", "mjernoMjesto",
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

const Rasvjeta = ({
    data,
    page,
    totalNumber,
    loadData = () => {},
    sendPageNumber = () => {}
}) => {
    const table = (<TableComponent
        items ={data ? data : []}
        fieldsToExclude={fieldsToExclude ? fieldsToExclude : []}
    />);

    const pagination = (<PaginationComponent
        totalNumber={totalNumber}
        setPageNumber={sendPageNumber}
        active={typeof page === "number" ? page : 1}
    />);

    return (
        <div style = {style}>
            <Button onClick={() => loadData()}>Dohvati lampe</Button>
            {table}
            {pagination}
        </div>
    );
};

export default createPlugin('Rasvjeta', {
    component: connect((state) => ({
        data: get(state, 'lighting.data'),
        page: get(state, 'lighting.pageNumber'),
        totalNumber: get(state, 'lighting.totalNumber')
    }), {
        loadData: getLightingData,
        sendPageNumber: setPageForLighting
    })(Rasvjeta),
    containers: {
        DrawerMenu: {
            name: "Rasvjeta",
            position: 3,
            text: <Message msgId="rasvjeta"/>,
            icon: <Glyphicon glyph="asterisk"/>,
            action: () => ({type: ''}),
            priority: 1,
            doNotHide: true
        }
    },
    epics,
    reducers: {
        lighting
    }
});
