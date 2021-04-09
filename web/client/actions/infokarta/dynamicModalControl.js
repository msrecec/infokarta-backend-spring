export const CLEAR_DYNAMIC_COMPONENT_STORE = 'CLEAR_DYNAMIC_COMPONENT_STORE';
export const SHOW_DYNAMIC_MODAL = 'SHOW_DYNAMIC_MODAL';
export const HIDE_DYNAMIC_MODAL = 'HIDE_DYNAMIC_MODAL';
export const ALTERNATE_MODAL_VISIBILITY = 'ALTERNATE_MODAL_VISIBILITY';
export const GET_COLUMNS_FOR_INSERT_FROM_DATABASE = 'GET_COLUMNS_FOR_INSERT_FROM_DATABASE';

export const showDynamicModal = (modalName, additionalObject = {}) => ({
    type: SHOW_DYNAMIC_MODAL,
    modalName,
    additionalObject
});

export const hideDynamicModal = () => ({
    type: HIDE_DYNAMIC_MODAL
});

export const alternateModalVisibility = (nameToHide, nameToShow, additionalObject = {}) => ({
    type: ALTERNATE_MODAL_VISIBILITY,
    nameToHide,
    nameToShow,
    additionalObject
});

export const clearDynamicComponentStore = () => ({
    type: CLEAR_DYNAMIC_COMPONENT_STORE
});

export const getColumnsForInsertFromDatabase = () => ({
    type: GET_COLUMNS_FOR_INSERT_FROM_DATABASE
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
