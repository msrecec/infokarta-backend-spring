export const SEND_SEARCH_REQUEST_FOR_DECEASED = "SEND_SEARCH_REQUEST_DECEASED";
export const DECEASED_RESPONSE_RECEIVED = "DECEASED_RESPONSE_RECEIVED";
export const SET_SEARCH_PARAMETERS_FOR_DECEASED = "SET_SEARCH_PARAMETERS_FOR_DECEASED";
export const RESET_SEARCH_PARAMETERS_FOR_DECEASED = "RESET_SEARCH_PARAMETERS_FOR_DECEASED";
export const SET_PAGE_FOR_DECEASED = "SET_PAGE_FOR_DECEASED";
export const RESET_PAGE_FOR_DECEASED = "RESET_PAGE_FOR_DECEASED";
// export const EDIT_DECEASED = "EDIT_DECEASED";
// export const INSERT_DECEASED = "INSERT_DECEASED";
export const ZOOM_TO_GRAVE_FROM_DECEASED = "ZOOM_TO_GRAVE_FROM_DECEASED";

export const sendSearchRequestForDeceased = () => ({
    type: SEND_SEARCH_REQUEST_FOR_DECEASED
});

export const deceasedResponseReceived = (deceased, totalNumber) => ({
    type: DECEASED_RESPONSE_RECEIVED,
    deceased,
    totalNumber
});

export const setSearchParametersForDeceased = (searchParameters) => ({
    type: SET_SEARCH_PARAMETERS_FOR_DECEASED,
    searchParameters
});

export const resetSearchParametersForDeceased = () => ({
    type: RESET_SEARCH_PARAMETERS_FOR_DECEASED
});

export const setPageForDeceased = (pageNumber) => ({
    type: SET_PAGE_FOR_DECEASED,
    pageNumber
});

// export const editDeceased = (itemToEdit) => ({
//     type: EDIT_DECEASED,
//     itemToEdit
// });

// export const insertDeceased = (itemToInsert) => ({
//     type: INSERT_DECEASED,
//     itemToInsert
// });

export const zoomToGraveFromDeceased = (graveId) => ({
    type: ZOOM_TO_GRAVE_FROM_DECEASED,
    graveId
});
