export const DECEASED_LOADED = 'DECEASED_LOADED';
export const LOAD_DECEASED = 'LOAD_DECEASED';

export const deceasedLoaded = (deceased, exclude, readonly) => ({
    type: DECEASED_LOADED,
    deceased,
    exclude,
    readonly
});

export const loadDeceased = (searchParameters) => ({
    type: LOAD_DECEASED,
    searchParameters
});
