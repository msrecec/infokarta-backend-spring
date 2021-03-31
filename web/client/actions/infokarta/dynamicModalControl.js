export const SHOW_EDIT_MODAL = 'SHOW_EDIT_MODAL';
export const SHOW_INSERT_MODAL = 'SHOW_INSERT_MODAL';
export const HIDE_EDIT_MODAL = 'HIDE_EDIT_MODAL';
export const HIDE_INSERT_MODAL = 'HIDE_INSERT_MODAL';
export const GENERATE_INSERT_FORM = 'GENERATE_INSERT_FORM';
export const SHOW_INSERT_CONFIRMATION_FORM = 'SHOW_INSERT_CONFIRMATION_FORM';
export const HIDE_INSERT_CONFIRMATION_FORM = 'HIDE_INSERT_CONFIRMATION_FORM';
export const CLEAR_ALL_DYNAMIC_FORMS = 'CLEAR_ALL_DYNAMIC_FORMS';

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
    type: SHOW_INSERT_CONFIRMATION_FORM,
    itemToCheck
});

export const hideInsertConfirmationModal = () => ({
    type: HIDE_INSERT_CONFIRMATION_FORM
});

export const clearAllDynamicForms = () => ({
    type: CLEAR_ALL_DYNAMIC_FORMS
});
