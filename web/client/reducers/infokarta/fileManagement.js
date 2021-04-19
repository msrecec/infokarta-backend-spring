import {
    IMAGES_LOADED_BY_ENTITY_ID,
    UPLOAD_NEW_IMAGE_RESPONSE,
    UPDATE_METADATA_IN_STORE_INFO
} from "../../actions/infokarta/fileManagement";
const fileManagement = (
    state = {
        files: [],
        entityFid: null,
        uploadResponse: null
    },
    action
) => {
    switch (action.type) {
    case IMAGES_LOADED_BY_ENTITY_ID: {
        return {
            ...state,
            files: action.response
        };
    }
    case UPLOAD_NEW_IMAGE_RESPONSE: {
        return {
            ...state,
            uploadResponse: action.response
        };
    }
    case UPDATE_METADATA_IN_STORE_INFO: {
        return {
            ...state,
            entityFid: action.entityFid
        };
    }
    default:
        return state;
    }
};

export default fileManagement;
