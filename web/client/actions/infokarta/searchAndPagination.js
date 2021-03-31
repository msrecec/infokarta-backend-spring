export const SET_PAGINATION_NUMBER = 'SET_PAGINATION_NUMBER';
export const RESET_PAGINATION_NUMBER = 'RESET_PAGINATION_NUMBER';
export const SET_SEARCH_PARAMETERS = 'SET_SEARCH_PARAMETERS';
export const RESET_SEARCH_PARAMETERS = 'RESET_SEARCH_PARAMETERS';
export const SEND_SEARCH_REQUEST = 'SEND_SEARCH_REQUEST';
export const STORE_SEARCH_RESULT = 'STORE_SEARCH_RESULT';

export const setPaginationNumber = (pageNumber) => ({
    type: SET_PAGINATION_NUMBER,
    pageNumber
});

export const resetPaginationNumber = () => ({
    type: RESET_PAGINATION_NUMBER
});

export const setSearchParameters = (searchParameters) => ({
    type: SET_SEARCH_PARAMETERS,
    searchParameters
});
export const resetSearchParameters = () => ({
    type: RESET_SEARCH_PARAMETERS
});
export const sendSearchRequest = () => ({
    type: SEND_SEARCH_REQUEST
});

export const resetSearchRequest = (result) => ({
    type: STORE_SEARCH_RESULT,
    result
});
