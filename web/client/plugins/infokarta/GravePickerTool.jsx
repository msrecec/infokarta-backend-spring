import React from 'react';
import { connect } from 'react-redux';
import { get } from 'lodash';

import {
    enableGravePickModal,
    disableGravePickModal,
    confirmGravePick,
    setGravePickMode
} from "../../actions/infokarta/pokojnici";

import {
    showEditModal,
    showInsertModal
} from "../../actions/infokarta/dynamicModalControl";

import { createPlugin } from '../../utils/PluginsUtils';

import pokojnici from '../../reducers/infokarta/pokojnici';
import dynamicModalControl from '../../reducers/infokarta/dynamicModalControl';

import * as epics from '../../epics/infokarta/pokojnici';

import GravePickerModal from '../../components/infokarta/GravePickerModal';

const GravePickerTool = ({
    show,
    mode,
    chosenGrave,
    showModal,
    hideModal,
    loadGrave,
    setChooseMode
}) => {

    const gravePickerModal = (<GravePickerModal
        show={show}
        mode={mode}
        chosenGrave={chosenGrave}
        showModal={showModal}
        hideModal={hideModal}
        loadGrave={loadGrave}
        setChooseMode={setChooseMode}
    />);

    return (
        <div>
            {gravePickerModal}
        </div>
    );
};

export default createPlugin('GravePickerTool', {
    component: connect((state) => ({
        show: get(state, 'pokojnici.chooseGraveModal'),
        mode: get(state, 'pokojnici.graveChooseMode'),
        chosenGrave: get(state, 'pokojnici.chosenGrave')
    }), {
        showModal: enableGravePickModal,
        hideModal: disableGravePickModal,
        loadGrave: confirmGravePick,
        setChooseMode: setGravePickMode
    })(GravePickerTool),
    epics,
    reducers: {
        pokojnici,
        dynamicModalControl
    }
});
