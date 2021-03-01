const DECEASED_LOADED = 'DECEASED_LOADED';

function deceasedLoaded(deceased) {
    console.log('action');
    return {
        type: DECEASED_LOADED,
        deceased
    };
}

function loadDeceased(data) {
    return (dispatch) => {
        console.log('dispatch');
        dispatch(deceasedLoaded(data));
    };
}

module.exports = {
    DECEASED_LOADED,
    deceasedLoaded,
    loadDeceased
};
