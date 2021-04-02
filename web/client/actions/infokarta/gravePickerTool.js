export const SHOW_GRAVE_PICK_MODAL = "SHOW_GRAVE_PICK_MODAL";
export const HIDE_GRAVE_PICK_MODAL = "HIDE_GRAVE_PICK_MODAL";
export const ENABLE_GRAVE_PICK_MODE = "ENABLE_GRAVE_PICK_MODE";
export const DISABLE_GRAVE_PICK_MODE = "DISABLE_GRAVE_PICK_MODE";
export const CONFIRM_GRAVE_PICK = "CONFIRM_GRAVE_PICK";
export const CLEAR_GRAVE_PICKER_TOOL_STORE = "CLEAR_GRAVE_PICKER_TOOL_STORE";

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

export const clearGravePickerToolStore =  () => ({
    type: CLEAR_GRAVE_PICKER_TOOL_STORE
});
