export const GET_LIGHTING_DATA = "GET_LIGHTING_DATA";
export const LIGHTING_DATA_RECEIVED = "LIGHTING_DATA_RECEIVED";
export const SET_PAGE_FOR_LIGHTING = "SET_PAGE_FOR_LIGHTING";
/* export const ZOOM_TO_LAMP_FROM_LIGHTING = "ZOOM_TO_LAMP_FROM_LIGHTING"; */
export const EDIT_LIGHTING = "EDIT_LIGHTING";
export const SET_SEARCH_PARAMETERS_FOR_LIGHTING = "SET_SEARCH_PARAMETERS_FOR_LIGHTING";
export const RESET_SEARCH_PARAMETERS_FOR_LIGHTING = "RESET_SEARCH_PARAMETERS_FOR_LIGHTING";

export const getLightingData = () => ({
    type: GET_LIGHTING_DATA
});

export const lightingDataReceived = (lighting, totalNumber) => ({
    type: LIGHTING_DATA_RECEIVED,
    lighting,
    totalNumber
});

export const setPageForLighting = (pageNumber) => ({
    type: SET_PAGE_FOR_LIGHTING,
    pageNumber
});

/* export const zoomToLampFromLighting = (geom) => ({
    type: ZOOM_TO_LAMP_FROM_LIGHTING,
    geom
}); */

export const editLighting = (itemToEdit) => ({
    type: EDIT_LIGHTING,
    itemToEdit
});

export const setSearchParametersForLighting = (searchParameters) => ({
    type: SET_SEARCH_PARAMETERS_FOR_LIGHTING,
    searchParameters
});

export const resetSearchParametersForLighting = () => ({
    type: RESET_SEARCH_PARAMETERS_FOR_LIGHTING
});
