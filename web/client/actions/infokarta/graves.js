export const SEND_SEARCH_REQUEST_FOR_GRAVES = "SEND_SEARCH_REQUEST_FOR_GRAVES";
export const SET_SEARCH_PARAMETERS_FOR_GRAVES = "SET_SEARCH_PARAMETERS_FOR_GRAVES";
export const RESET_SEARCH_PARAMETERS_FOR_GRAVES = "RESET_SEARCH_PARAMETERS_FOR_GRAVES";
export const GRAVES_RESPONSE_RECEIVED = "GRAVES_RESPONSE_RECEIVED";
export const ZOOM_TO_GRAVE_FROM_GRAVES = "ZOOM_TO_GRAVE_FROM_GRAVES";

export const sendSearchRequestForGraves = () => ({
    type: SEND_SEARCH_REQUEST_FOR_GRAVES
});

export const setSearchParametersForGraves = (searchParameters) => ({
    type: SET_SEARCH_PARAMETERS_FOR_GRAVES,
    searchParameters
});

export const resetSearchParametersForGraves = () => ({
    type: RESET_SEARCH_PARAMETERS_FOR_GRAVES
});

export const gravesResponseReceived = (data, count) => ({
    type: GRAVES_RESPONSE_RECEIVED,
    data,
    count
});

export const zoomToGraveFromGraves = (geom) => ({
    type: ZOOM_TO_GRAVE_FROM_GRAVES,
    geom
});
