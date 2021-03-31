import {
    SHOW_EDIT_MODAL,
    SHOW_INSERT_MODAL,
    HIDE_EDIT_MODAL,
    HIDE_INSERT_MODAL,
    GENERATE_INSERT_FORM,
    SHOW_INSERT_CONFIRMATION_FORM,
    HIDE_INSERT_CONFIRMATION_FORM,
    CLEAR_ALL_DYNAMIC_FORMS
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    itemToCheck: {},
    editModalVisible: false,
    insertModalVisible: false,
    insertConfirmationModalVisible: false
}, action) => {
    switch (action.type) {
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
    case SHOW_INSERT_CONFIRMATION_FORM: {
        return {
            ...state,
            itemToCheck: action.itemToCheck,
            insertModalVisible: false,
            insertConfirmationModalVisible: true
        };
    }
    case HIDE_INSERT_CONFIRMATION_FORM: {
        return {
            ...state,
            insertModalVisible: true,
            insertConfirmationModalVisible: false
        };
    }
    case CLEAR_ALL_DYNAMIC_FORMS: {
        return {
            ...state,
            itemToEdit: {},
            itemToCheck: {},
            editModalVisible: false,
            insertModalVisible: false,
            insertConfirmationModalVisible: false
        };
    }
    default:
        return state;
    }
};

export default dynamicModalControl;
