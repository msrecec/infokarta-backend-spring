import {
    SET_PAGINATION_NUMBER,
    RESET_PAGINATION_NUMBER,
    SET_SEARCH_PARAMETERS,
    RESET_SEARCH_PARAMETERS
    // SEND_SEARCH_REQUEST,
    // STORE_SEARCH_RESULT
} from '../../actions/infokarta/searchAndPagination';

const searchAndPagination = (state = {
    pageNumber: 1,
    searchParameters: {}
}, action) => {
    switch (action.type) {
    case SET_PAGINATION_NUMBER: {
        return {
            ...state,
            pageNumber: action.pageNumber
        };
    }
    case RESET_PAGINATION_NUMBER: {
        return {
            ...state,
            pageNumber: 1
        };
    }
    case SET_SEARCH_PARAMETERS: {
        return {
            ...state,
            searchParameters: action.searchParameters
        };
    }
    case RESET_SEARCH_PARAMETERS: {
        return {
            ...state,
            searchParameters: {}
        };
    }
    default:
        return state;
    }
};

export default searchAndPagination;
