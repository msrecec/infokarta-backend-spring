import {
    FILES_LOADED_BY_ENTITY_ID
} from "../../actions/infokarta/fileManagement";
const fileManagement = (
    state = {
        files: []
    },
    action
) => {
    switch (action.type) {
    case FILES_LOADED_BY_ENTITY_ID: {
        console.log('!!! reducer', action.response);
        return {
            ...state,
            files: action.response
        };
    }
    default:
        return state;
    }
};

export default fileManagement;
