import {
    DECEASED_LOADED
} from '../../actions/infokarta/pokojnici';

const pokojnici = (state = {
    deceased: []
}, action) => {
    switch (action.type) {
    case DECEASED_LOADED: {
        return {
            ...state,
            deceased: action.deceased,
            fieldsToExclude: action.exclude,
            readOnlyFields: action.readonly
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
