export const DECEASED_LOADED = "DECEASED_LOADED";
export const LOAD_DECEASED = "LOAD_DECEASED";
export const DECEASED_LOADED_BY_PAGE = "DECEASED_LOADED_BY_PAGE";
export const LOAD_DECEASED_BY_PAGE = "LOAD_DECEASED_BY_PAGE";
export const EDIT_DECEASED = "EDIT_DECEASED";

export const deceasedLoaded = (deceased, totalNumber) => ({
    type: DECEASED_LOADED,
    deceased,
    totalNumber
});

export const loadDeceased = (searchParameters) => ({
    type: LOAD_DECEASED,
    searchParameters
});

export const deceasedLoadedByPage = (deceased) => ({
    type: DECEASED_LOADED_BY_PAGE,
    deceased
});

export const loadDeceasedByPage = (pageNumber) => ({
    type: LOAD_DECEASED_BY_PAGE,
    pageNumber
});

export const editDeceased = (itemToEdit) => ({
    type: EDIT_DECEASED,
    itemToEdit
});
