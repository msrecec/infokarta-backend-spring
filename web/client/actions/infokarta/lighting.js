export const GET_LIGHTING_DATA = "GET_LIGHTING_DATA";
export const LIGHTING_DATA_RECEIVED = "LIGHTING_DATA_RECEIVED";
export const SET_PAGE_FOR_LIGHTING = "SET_PAGE_FOR_LIGHTING";

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
