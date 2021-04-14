import {
    LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW,
    CLOSE_DETAILS_AND_DOCS_VIEW,
    CLEAR_DETAILS_AND_DOCS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";
const detailsAndDocuments = (
    state = {
        item: {},
        showDetails: false
    },
    action
) => {
    switch (action.type) {
    case LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            item: action.item,
            showDetails: true
        };
    }
    case CLOSE_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            showDetails: false
        };
    }
    case CLEAR_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            showDetails: false,
            item: {}
        };
    }
    default:
        return state;
    }
};

export default detailsAndDocuments;
