import {
    LOAD_DATA_FOR_DETAILS_PLUGIN
} from "../../actions/infokarta/detailsAndDocuments";
const detailsAndDocuments = (
    state = {
        item: {}
    },
    action
) => {
    switch (action.type) {
    case LOAD_DATA_FOR_DETAILS_PLUGIN: {
        return {
            ...state,
            files: action.response
        };
    }
    default:
        return state;
    }
};

export default detailsAndDocuments;
