export const SHOW_DYNAMIC_MODAL = 'SHOW_DYNAMIC_MODAL';
export const HIDE_DYNAMIC_MODAL = 'HIDE_DYNAMIC_MODAL';
export const ALTERNATE_MODAL_VISIBILITY = 'ALTERNATE_MODAL_VISIBILITY';
export const CLEAR_DYNAMIC_COMPONENT_STORE = 'CLEAR_DYNAMIC_COMPONENT_STORE';
export const GET_COLUMNS_FOR_INSERT_FROM_DATABASE = 'GET_COLUMNS_FOR_INSERT_FROM_DATABASE';
export const GET_ITEM_FOR_EDIT_FROM_DATABASE = 'GET_ITEM_FOR_EDIT_FROM_DATABASE';
export const ACQUIRE_CURRENT_PLUGIN_NAME = 'ACQUIRE_CURRENT_PLUGIN_NAME';

export const showDynamicModal = (modalType, additionalObject = {}) => ({
    type: SHOW_DYNAMIC_MODAL,
    modalType,
    additionalObject
});

export const hideDynamicModal = () => ({
    type: HIDE_DYNAMIC_MODAL
});

export const alternateModalVisibility = (typeToHide, typeToShow, additionalObject = {}) => ({
    type: ALTERNATE_MODAL_VISIBILITY,
    typeToHide,
    typeToShow,
    additionalObject
});

export const clearDynamicComponentStore = () => ({
    type: CLEAR_DYNAMIC_COMPONENT_STORE
});

export const getColumnsForInsertFromDatabase = () => ({
    type: GET_COLUMNS_FOR_INSERT_FROM_DATABASE
});

export const getItemForEditFromDatabase = (fid) => ({
    type: GET_ITEM_FOR_EDIT_FROM_DATABASE,
    fid
});

export const acquireCurrentPluginName = (name) => ({
    type: ACQUIRE_CURRENT_PLUGIN_NAME,
    name
});

import { success, error } from '../notifications';
export function insertSuccessful(title, message, values = "") {
    return success({
        title: title,
        message: message,
        values: {values}
    });
}

export function insertUnsuccessful(title, message, values = "") {
    return error({
        title: title,
        message: message,
        values: {values}
    });
}
