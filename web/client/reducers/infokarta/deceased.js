import {
    SET_SEARCH_PARAMETERS_FOR_DECEASED,
    RESET_SEARCH_PARAMETERS_FOR_DECEASED,
    DECEASED_RESPONSE_RECEIVED,
    SET_PAGE_FOR_DECEASED
} from "../../actions/infokarta/deceased";
const deceased = (
    state = {
        data: [],
        totalNumber: null,
        searchParameters: {},
        pageNumber: 1
    },
    action
) => {
    switch (action.type) {
    case SET_SEARCH_PARAMETERS_FOR_DECEASED: {
        return {
            ...state,
            searchParameters: action.searchParameters,
            pageNumber: 1
        };
    }
    case RESET_SEARCH_PARAMETERS_FOR_DECEASED: {
        return {
            ...state,
            searchParameters: {},
            pageNumber: 1
        };
    }
    case SET_PAGE_FOR_DECEASED: {
        // console.log('!!! setPage', state);
        return {
            ...state,
            pageNumber: parseInt(action.pageNumber, 10)
        };
    }
    case DECEASED_RESPONSE_RECEIVED: {
        // console.log('!!! responseRec', state);
        return {
            ...state,
            data: action.deceased,
            totalNumber: parseInt(action.totalNumber, 10)
        };
    }
    default:
        return state;
    }
};

export default deceased;
