import {
    LIGHTING_DATA_RECEIVED,
    SET_PAGE_FOR_LIGHTING
} from '../../actions/infokarta/lighting';

const lighting = (
    state = {
        data: [],
        totalNumber: null,
        pageNumber: 1
    },
    action
) => {
    switch (action.type) {
    case LIGHTING_DATA_RECEIVED: {
        /* console.log('Hahalol1'); */
        return {
            ...state,
            data: action.lighting,
            totalNumber: action.totalNumber
        };
    }
    case SET_PAGE_FOR_LIGHTING: {
        return {
            ...state,
            pageNumber: action.pageNumber
        };
    }
    default: {
        /* console.log('hahalol ali default'); */
        return state;
    }
    }
};

export default lighting;
