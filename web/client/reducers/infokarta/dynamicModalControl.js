import { mapValues } from 'lodash';
import {
    CLEAR_DYNAMIC_COMPONENT_STORE,
    SHOW_DYNAMIC_MODAL,
    HIDE_DYNAMIC_MODAL,
    ALTERNATE_MODAL_VISIBILITY
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    itemToCheck: {},
    itemToInsert: [],
    modals: {},
    editModalVisible: false,
    insertModalVisible: false,
    insertConfirmationModalVisible: false
}, action) => {
    switch (action.type) {
    case SHOW_DYNAMIC_MODAL: {
        let temp = state.modals;
        temp[action.modalName] = true;
        // console.log(action.modalName.includes("Edit"));
        return {
            ...state,
            modals: temp,
            itemToEdit: action.modalName.includes("Edit") ? action.additionalObject : {},
            itemToInsert: action.modalName.includes("Insert") ? action.additionalObject : []
        };

    }
    case HIDE_DYNAMIC_MODAL: {
        let temp = state.modals;
        return {
            ...state,
            modals: mapValues(temp, () => false)
        };
    }
    case ALTERNATE_MODAL_VISIBILITY: {
        let temp = state.modals;
        temp[action.nameToHide] = false;
        temp[action.nameToShow] = true;
        return {
            ...state,
            modals: temp,
            itemToCheck: action.additionalObject
        };
    }
    case CLEAR_DYNAMIC_COMPONENT_STORE: {
        let temp = state.modals;
        return {
            ...state,
            itemToEdit: {},
            itemToCheck: {},
            modals: mapValues(temp, () => false)
        };
    }
    default:
        return state;
    }
};

export default dynamicModalControl;
