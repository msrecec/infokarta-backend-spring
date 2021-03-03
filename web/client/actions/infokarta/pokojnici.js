export const DECEASED_LOADED = 'DECEASED_LOADED';
export const LOAD_DECEASED = 'LOAD_DECEASED';

export const deceasedLoaded = (deceased) => ({
    type: DECEASED_LOADED,
    deceased
});

export const loadDeceased = () => ({
    type: LOAD_DECEASED
});
