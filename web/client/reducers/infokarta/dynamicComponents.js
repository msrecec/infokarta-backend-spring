import {
    CLEAR_DYNAMIC_COMPONENT_STORE,
    SHOW_DYNAMIC_MODAL,
    HIDE_DYNAMIC_MODAL,
    ALTERNATE_MODAL_VISIBILITY,
    ACQUIRE_CURRENT_PLUGIN_NAME,
    CLEAR_ACTIVE_PLUGIN
} from '../../actions/infokarta/dynamicComponents';
import { mapValues } from 'lodash';

const dynamicComponents = (
    state = {
        itemToEdit: {},
        itemToCheck: {},
        itemToInsert: [],
        modals: {},
        activePlugin: null
    },
    action
) => {
    switch (action.type) {
    case SHOW_DYNAMIC_MODAL: {
        console.log("!!! show dynamic modal", action, state);
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
        console.log("!!! alternate modal visibility", action, state);
        let temp = {...state.modals};
        temp[action.typeToHide] = false;
        temp[action.typeToShow] = true;
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
        };
    }
    case CLEAR_ACTIVE_PLUGIN: {
        return {
            ...state,
            activePlugin: ""
        };
    }
    default:
        return state;
    }
};

export default dynamicComponents;
