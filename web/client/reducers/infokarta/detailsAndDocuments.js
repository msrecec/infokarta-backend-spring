import {
    LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW,
    CLOSE_DETAILS_AND_DOCS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";
const detailsAndDocuments = (
    state = {
        item: {},
        tableHeight: "600px",
        showDetails: "none"
    },
    action
) => {
    switch (action.type) {
    case LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            item: action.item,
            tableHeight: "150px",
            showDetails: "block"
        };
    }
    case CLOSE_DETAILS_AND_DOCS_VIEW: {
        return {
            ...state,
            tableHeight: "600px",
            showDetails: "none"
        };
    }
    default:
        return state;
    }
};

export default detailsAndDocuments;
