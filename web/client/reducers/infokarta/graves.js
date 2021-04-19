import {
    SET_SEARCH_PARAMETERS_FOR_GRAVES,
    RESET_SEARCH_PARAMETERS_FOR_GRAVES,
    GRAVES_RESPONSE_RECEIVED
} from "../../actions/infokarta/graves";
const graves = (
    state = {
        data: [],
        totalNumber: null,
        searchParameters: {},
        pageNumber: 1
    },
    action
) => {
    switch (action.type) {
    case SET_SEARCH_PARAMETERS_FOR_GRAVES: {
        return {
            ...state,
            searchParameters: action.searchParameters,
            pageNumber: 1
        };
    }
    case RESET_SEARCH_PARAMETERS_FOR_GRAVES: {
        return {
            ...state,
            searchParameters: {},
            pageNumber: 1
        };
    }
    case GRAVES_RESPONSE_RECEIVED: {
        return {
            ...state,
            data: action.data
            // totalNumber: action.count
        };
    }
    default:
        return state;
    }
};

export default graves;
