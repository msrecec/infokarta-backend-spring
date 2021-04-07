export const GET_LIGHTING_DATA = "GET_LIGHTING_DATA";
export const LIGHTING_DATA_RECEIVED = "LIGHTING_DATA_RECEIVED";

export const getLightingData = () => ({
    type: GET_LIGHTING_DATA
});

export const lightingDataReceived = (lighting) => ({
    type: LIGHTING_DATA_RECEIVED,
    lighting
});
