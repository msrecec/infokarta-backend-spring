export const DECEASED_LOADED = "DECEASED_LOADED";
export const LOAD_DECEASED = "LOAD_DECEASED";
export const DECEASED_LOADED_BY_PAGE = "DECEASED_LOADED_BY_PAGE";
export const LOAD_DECEASED_BY_PAGE = "LOAD_DECEASED_BY_PAGE";
export const EDIT_DECEASED = "EDIT_DECEASED";
export const INSERT_DECEASED = "INSERT_DECEASED";
export const ZOOM_TO_GRAVE = "ZOOM_TO_GRAVE";
export const SHOW_GRAVE_PICK_MODAL = "SHOW_GRAVE_PICK_MODAL";
export const HIDE_GRAVE_PICK_MODAL = "HIDE_GRAVE_PICK_MODAL";
export const ENABLE_GRAVE_PICK_MODE = "ENABLE_GRAVE_PICK_MODE";
export const DISABLE_GRAVE_PICK_MODE = "DISABLE_GRAVE_PICK_MODE";
export const CONFIRM_GRAVE_PICK = "CONFIRM_GRAVE_PICK";

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

export const showGravePickModal = (mode, graveId, grave) => ({
    type: SHOW_GRAVE_PICK_MODAL,
    mode, // "initial", "single", "multiple"
    graveId,
    grave
});

export const hideGravePickModal = () => ({
    type: HIDE_GRAVE_PICK_MODAL
});

export const enableGravePickMode = () => ({
    type: ENABLE_GRAVE_PICK_MODE
});

export const disableGravePickMode = () => ({
    type: DISABLE_GRAVE_PICK_MODE
});

export const confirmGravePick = () => ({
    type: CONFIRM_GRAVE_PICK
});
