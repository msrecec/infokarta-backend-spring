import {
    SHOW_DYNAMIC_MODAL,
    HIDE_DYNAMIC_MODAL
} from '../../actions/infokarta/dynamicModalControl';

const dynamicModalControl = (state = {
    itemToEdit: {},
    modalVisible: false
}, action) => {
    switch (action.type) {
    case SHOW_DYNAMIC_MODAL: {
        return {
            ...state,
            itemToEdit: action.itemToEdit,
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
