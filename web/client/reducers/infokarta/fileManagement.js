import {
    IMAGES_LOADED_BY_ENTITY_ID,
    UPLOAD_NEW_IMAGE_RESPONSE
} from "../../actions/infokarta/fileManagement";
const fileManagement = (
    state = {
        files: [],
        uploadResponse: null
    },
    action
) => {
    switch (action.type) {
    case IMAGES_LOADED_BY_ENTITY_ID: {
        console.log(action);
        return {
            ...state,
            files: action.response
        };
    }
    case UPLOAD_NEW_IMAGE_RESPONSE: {
        console.log(action);
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
