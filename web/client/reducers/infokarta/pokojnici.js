const {
    DECEASED_LOADED
} = require('../../actions/infokarta/pokojnici');

function pokojnici(state = {
    deceased: []
}, action) {
    switch (action.type) {
    case DECEASED_LOADED: {
        console.log('reducer1');
        return {
            deceased: action.deceased
        };
    }
    default:
        console.log('reducer2');
        return state;
    }
}

module.exports = pokojnici;
