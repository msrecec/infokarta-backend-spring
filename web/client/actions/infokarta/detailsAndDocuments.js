export const GET_DATA_FOR_DETAILS_VIEW = 'GET_DATA_FOR_DETAILS_VIEW';
export const CLEAR_DETAILS_AND_DOCS_VIEW = 'CLEAR_DETAILS_AND_DOCS_VIEW';
export const STORE_DETAILS_VIEW_RESPONSE = 'STORE_DETAILS_VIEW_RESPONSE';

export const getDataForDetailsView = (fid, /* fk = null ,*/ additionalPluginTitle = "") => ({
    type: GET_DATA_FOR_DETAILS_VIEW,
    fid,
    // fk,
    additionalPluginTitle
});

export const storeDetailsViewResponse = (response, pluginName) => ({
    type: STORE_DETAILS_VIEW_RESPONSE,
    response,
    pluginName
});

export const clearDetailsAndDocsView = () => ({
    type: CLEAR_DETAILS_AND_DOCS_VIEW
});
