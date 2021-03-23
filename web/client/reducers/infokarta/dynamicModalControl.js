import {
    SHOW_EDIT_MODAL,
    SHOW_INSERT_MODAL,
    HIDE_EDIT_MODAL,
    HIDE_INSERT_MODAL,
    GENERATE_INSERT_FORM
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    editModalVisible: false,
    insertModalVisible: false
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
    default:
        return state;
    }
};

export default dynamicModalControl;
