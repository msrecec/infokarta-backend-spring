import {
    DECEASED_LOADED
} from '../../actions/infokarta/pokojnici';

const pokojnici = (state = {
    deceased: []
}, action) => {
    switch (action.type) {
    case DECEASED_LOADED: {
        // console.log('reducer1');
        return {
            ...state,
            deceased: action.deceased,
            fieldsToExclude: action.exclude,
            readOnlyFields: action.readonly
        };
    }
    default:
        // console.log('reducer2');
        return state;
    }
};

export default pokojnici;
