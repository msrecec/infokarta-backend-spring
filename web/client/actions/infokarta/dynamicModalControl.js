export const SHOW_DYNAMIC_MODAL = 'SHOW_DYNAMIC_MODAL';
export const HIDE_DYNAMIC_MODAL = 'HIDE_DYNAMIC_MODAL';

export const showDynamicModal = (itemToEdit) => ({
    type: SHOW_DYNAMIC_MODAL,
    itemToEdit
});

export const hideDynamicModal = () => ({
    type: HIDE_DYNAMIC_MODAL
});
