import {
    SHOW_EDIT_MODAL,
    SHOW_INSERT_MODAL,
    HIDE_DYNAMIC_MODAL
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    modalVisible: false
}, action) => {
    switch (action.type) {
    case SHOW_EDIT_MODAL: {
        return {
            ...state,
            itemToEdit: action.itemToEdit,
            modalVisible: true
        };
    }
    case SHOW_INSERT_MODAL: {
        return {
            ...state,
            itemToInsert: action.itemToInsert,
            modalVisible: true
        };
    }
    case HIDE_DYNAMIC_MODAL: {
        return {
            ...state,
            modalVisible: false
        };
    }
    default:
        return state;
    }
};

export default dynamicModalControl;
