import {
    LIGHTING_DATA_RECEIVED
} from '../../actions/infokarta/lighting';

const lighting = (
    state = {
        data: []
    },
    action
) => {
    switch (action.type) {
    case LIGHTING_DATA_RECEIVED: {
        /* console.log('Hahalol1'); */
        return {
            ...state,
            data: action.lighting
        };
    }
    default: {
        /* console.log('hahalol ali default'); */
        return state;
    }
    }
};

export default lighting;
