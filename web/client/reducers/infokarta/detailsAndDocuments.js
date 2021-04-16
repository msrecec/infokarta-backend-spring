import {
    STORE_DETAILS_VIEW_RESPONSE,
    CLEAR_DETAILS_AND_DOCS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";
const detailsAndDocuments = (
    state = {
        items: {},
        showDetails: false
    },
    action
) => {
    switch (action.type) {
    case STORE_DETAILS_VIEW_RESPONSE: {
        return {
            ...state,
            showDetails: true,
            items: action.responseArray
        };
    }
    case CLEAR_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            showDetails: false,
            items: {}
        };
    }
    default:
        return state;
    }
};

export default detailsAndDocuments;
