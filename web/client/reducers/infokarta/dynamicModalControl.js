import { mapValues } from 'lodash';
import {
    SHOW_EDIT_MODAL,
    SHOW_INSERT_MODAL,
    HIDE_EDIT_MODAL,
    HIDE_INSERT_MODAL,
    GENERATE_INSERT_FORM,
    SHOW_INSERT_CONFIRMATION_MODAL,
    HIDE_INSERT_CONFIRMATION_MODAL,
    CLEAR_DYNAMIC_COMPONENT_STORE,
    SHOW_DYNAMIC_MODAL,
    HIDE_DYNAMIC_MODAL,
    ALTERNATE_MODAL_VISIBILITY
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    itemToCheck: {},
    modals: {},
    editModalVisible: false,
    insertModalVisible: false,
    insertConfirmationModalVisible: false
}, action) => {
    switch (action.type) {
    case SHOW_DYNAMIC_MODAL: {
        let temp = state.modals;
        temp[action.modalName] = true;
        console.log(action.modalName.includes("Edit"));
        return {
            ...state,
            modals: temp,
            itemToEdit: action.modalName.includes("Edit") ? action.additionalObject : {}
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
    case SHOW_EDIT_MODAL: {
        return {
            ...state,
            itemToEdit: action.itemToEdit,
            editModalVisible: true
        };
    }
    case SHOW_INSERT_MODAL: {
        return {
            ...state
        };
    }
    case HIDE_EDIT_MODAL: {
        return {
            ...state,
            editModalVisible: false
        };
    }
    case HIDE_INSERT_MODAL: {
        return {
            ...state,
            insertModalVisible: false
        };
    }
    case GENERATE_INSERT_FORM: {
        return {
            ...state,
            itemToInsert: action.itemToInsert,
            insertModalVisible: true
        };
    }
    case SHOW_INSERT_CONFIRMATION_MODAL: {
        return {
            ...state,
            itemToCheck: action.itemToCheck,
            insertModalVisible: false,
            insertConfirmationModalVisible: true
        };
    }
    case HIDE_INSERT_CONFIRMATION_MODAL: {
        return {
            ...state,
            insertModalVisible: true,
            insertConfirmationModalVisible: false
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
