import {
    FILES_LOADED_BY_ENTITY_ID,
    UPLOAD_NEW_FILE_RESPONSE
} from "../../actions/infokarta/fileManagement";
const fileManagement = (
    state = {
        files: [],
        uploadResponse: null
    },
    action
) => {
    switch (action.type) {
    case FILES_LOADED_BY_ENTITY_ID: {
        return {
            ...state,
            files: action.response
        };
    }
    case UPLOAD_NEW_FILE_RESPONSE: {
        return {
            ...state,
            uploadResponse: action.response
        };
    }
    default:
        return state;
    }
};

export default fileManagement;
