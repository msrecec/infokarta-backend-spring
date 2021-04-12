export const LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW = 'LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW';
export const CLOSE_DETAILS_AND_DOCS_VIEW = 'CLOSE_DETAILS_AND_DOCS_VIEW';

export const loadDataIntoDetailsAndDocsView = (item) => ({
    type: LOAD_DATA_INTO_DETAILS_AND_DOCS_VIEW,
    item
});

export const closeDetailsAndDocsView = () => ({
    type: CLOSE_DETAILS_AND_DOCS_VIEW
});
