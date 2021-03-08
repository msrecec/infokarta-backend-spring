export const DECEASED_LOADED = "DECEASED_LOADED";
export const LOAD_DECEASED = "LOAD_DECEASED";
export const DECEASED_LOADED_BY_PAGE = "DECEASED_LOADED_BY_PAGE";
export const LOAD_DECEASED_BY_PAGE = "LOAD_DECEASED_BY_PAGE";

export const deceasedLoaded = (deceased) => ({
    type: DECEASED_LOADED,
    deceased
});

export const loadDeceased = () => ({
    type: LOAD_DECEASED
});

export const deceasedLoadedByPage = (deceased) => ({
    type: DECEASED_LOADED_BY_PAGE,
    deceased
});

export const loadDeceasedByPage = (pageNumber) => ({
    type: LOAD_DECEASED_BY_PAGE,
    pageNumber
});
