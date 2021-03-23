export const DECEASED_LOADED = "DECEASED_LOADED";
export const LOAD_DECEASED = "LOAD_DECEASED";
export const DECEASED_LOADED_BY_PAGE = "DECEASED_LOADED_BY_PAGE";
export const LOAD_DECEASED_BY_PAGE = "LOAD_DECEASED_BY_PAGE";
export const EDIT_DECEASED = "EDIT_DECEASED";
export const INSERT_DECEASED = "INSERT_DECEASED";
export const ZOOM_TO_GRAVE = "ZOOM_TO_GRAVE";
export const START_CHOOSE_GRAVE_MODE = "START_CHOOSE_GRAVE_MODE";
export const END_CHOOSE_GRAVE_MODE = "END_CHOOSE_GRAVE_MODE";

export const deceasedLoaded = (deceased, totalNumber) => ({
    type: DECEASED_LOADED,
    deceased,
    totalNumber
});

export const loadDeceased = (searchParameters) => ({
    type: LOAD_DECEASED,
    searchParameters
});

export const editDeceased = (itemToEdit) => ({
    type: EDIT_DECEASED,
    itemToEdit
});

export const insertDeceased = (itemToInsert) => ({
    type: INSERT_DECEASED,
    itemToInsert
});

export const zoomToGrave = (graveId) => ({
    type: ZOOM_TO_GRAVE,
    graveId
});

export const startChooseGraveMode = () => ({
    type: START_CHOOSE_GRAVE_MODE
});

export const endChooseGraveMode = (chosenGrave) => ({
    type: END_CHOOSE_GRAVE_MODE,
    chosenGrave
});

