import { mapValues } from 'lodash';
import {
    CLEAR_DYNAMIC_COMPONENT_STORE,
    SHOW_DYNAMIC_MODAL,
    HIDE_DYNAMIC_MODAL,
    ALTERNATE_MODAL_VISIBILITY,
    ACQUIRE_CURRENT_PLUGIN_NAME
} from '../../actions/infokarta/dynamicComponents';

const dynamicComponents = (state = {
    itemToEdit: {},
    itemToCheck: {},
    itemToInsert: [],
    modals: {},
    activePlugin: ""
    // editModalVisible: false,
    // insertModalVisible: false,
    // insertConfirmationModalVisible: false,
    // editModalName: "",
    // insertModalName: "",
    // insertConfirmationModalName: ""
}, action) => {
    switch (action.type) {
    case SHOW_DYNAMIC_MODAL: {
        let temp = {...state.modals};
        temp[state.activePlugin + action.modalType] = true;
        return {
            ...state,
            modals: temp,
            itemToEdit: action.modalType === "Edit" ? action.additionalObject : {},
            itemToInsert: action.modalType === "Insert" ? action.additionalObject : []
        };
    }
    case HIDE_DYNAMIC_MODAL: {
        let temp = {...state.modals};
        return {
            ...state,
            modals: mapValues(temp, () => false)
        };
    }
    case ALTERNATE_MODAL_VISIBILITY: {
        let temp = {...state.modals};
        temp[state.activePlugin + action.typeToHide] = false;
        temp[state.activePlugin + action.typeToShow] = true;
        return {
            ...state,
            modals: temp,
            itemToCheck: action.additionalObject
        };
    }
    case CLEAR_DYNAMIC_COMPONENT_STORE: {
        let temp = {...state.modals};
        return {
            ...state,
            itemToEdit: {},
            itemToCheck: {},
            modals: mapValues(temp, () => false)
        };
    }
    case ACQUIRE_CURRENT_PLUGIN_NAME: {
        return {
            ...state,
            activePlugin: action.name
            // editModalName: action.name + "Edit",
            // insertModalName: action.name + "Insert",
            // insertConfirmationModalName: action.name + "Confirmation"
        };
    }
    default:
        return state;
    }
};

export default dynamicComponents;
