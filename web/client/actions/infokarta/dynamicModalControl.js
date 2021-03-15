export const SHOW_EDIT_MODAL = 'SHOW_EDIT_MODAL';
export const HIDE_DYNAMIC_MODAL = 'HIDE_DYNAMIC_MODAL';
export const SHOW_INSERT_MODAL = 'SHOW_INSERT_MODAL';
export const GENERATE_INSERT_FORM = 'GENERATE_INSERT_FORM';

export const showEditModal = (itemToEdit) => ({
    type: SHOW_EDIT_MODAL,
    itemToEdit
});

export const showInsertModal = () =>({
    type: SHOW_INSERT_MODAL
});

export const generateInsertForm = (itemToInsert) => ({
    type: GENERATE_INSERT_FORM,
    itemToInsert
});

export const hideDynamicModal = () => ({
    type: HIDE_DYNAMIC_MODAL
});
