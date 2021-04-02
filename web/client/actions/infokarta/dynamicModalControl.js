export const SHOW_EDIT_MODAL = 'SHOW_EDIT_MODAL';
export const SHOW_INSERT_MODAL = 'SHOW_INSERT_MODAL';
export const HIDE_EDIT_MODAL = 'HIDE_EDIT_MODAL';
export const HIDE_INSERT_MODAL = 'HIDE_INSERT_MODAL';
export const GENERATE_INSERT_FORM = 'GENERATE_INSERT_FORM';
export const SHOW_INSERT_CONFIRMATION_MODAL = 'SHOW_INSERT_CONFIRMATION_MODAL';
export const HIDE_INSERT_CONFIRMATION_MODAL = 'HIDE_INSERT_CONFIRMATION_MODAL';
export const CLEAR_DYNAMIC_COMPONENT_STORE = 'CLEAR_DYNAMIC_COMPONENT_STORE';

export const showEditModal = (itemToEdit) => ({
    type: SHOW_EDIT_MODAL,
    itemToEdit
});

export const showInsertModal = () =>({
    type: SHOW_INSERT_MODAL
});

export const hideEditModal = () => ({
    type: HIDE_EDIT_MODAL
});

export const hideInsertModal = () => ({
    type: HIDE_INSERT_MODAL
});

export const generateInsertForm = (itemToInsert) => ({
    type: GENERATE_INSERT_FORM,
    itemToInsert
});

export const showInsertConfirmationModal = (itemToCheck) => ({
    type: SHOW_INSERT_CONFIRMATION_MODAL,
    itemToCheck
});

export const hideInsertConfirmationModal = () => ({
    type: HIDE_INSERT_CONFIRMATION_MODAL
});

export const clearDynamicComponentStore = () => ({
    type: CLEAR_DYNAMIC_COMPONENT_STORE
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
