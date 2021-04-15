import {
    IMAGES_LOADED_BY_ENTITY_ID,
    UPLOAD_NEW_IMAGE_RESPONSE,
    UPDATE_METADATA_IN_STORE_INFO
} from "../../actions/infokarta/fileManagement";
const fileManagement = (
    state = {
        files: [],
        entityName: null,
        entityFid: null,
        uploadResponse: null
    },
    action
) => {
    switch (action.type) {
    case IMAGES_LOADED_BY_ENTITY_ID: {
        console.log("ovo je log akcije reducera: ", action);
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
    case UPDATE_METADATA_IN_STORE_INFO: {
        console.log("Log novog reducera: ", action);
        return {
            ...state,
            entityName: action.entityName,
            entityFid: action.entityFid
        };
    }
    default:
        return state;
    }
};

export default fileManagement;
