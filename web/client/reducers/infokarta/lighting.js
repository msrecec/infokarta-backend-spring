import {
    LIGHTING_DATA_RECEIVED,
    SET_PAGE_FOR_LIGHTING,
    SET_SEARCH_PARAMETERS_FOR_LIGHTING,
    RESET_SEARCH_PARAMETERS_FOR_LIGHTING
} from '../../actions/infokarta/lighting';

const lighting = (
    state = {
        data: [],
        totalNumber: null,
        searchParameters: {},
        pageNumber: 1
    },
    action
) => {
    switch (action.type) {
    case LIGHTING_DATA_RECEIVED: {
        return {
            ...state,
            data: action.lighting,
            totalNumber: action.totalNumber
        };
    }
    case SET_PAGE_FOR_LIGHTING: {
        return {
            ...state,
            pageNumber: action.pageNumber
        };
    }
    /*     case SET_SEARCH_PARAMETERS_FOR_LIGHTING: {
        return {
            ...state,
            searchParameters: action.searchParameters,
            pageNumber: 1
        };
    }
    case RESET_SEARCH_PARAMETERS_FOR_LIGHTING: {
        return {
            ...state,
            searchParameters: {},
            pageNumber: 1
        };
    } */
    default: {
        return state;
    }
    }
};

// SEARCH REDUCERI

export default lighting;
