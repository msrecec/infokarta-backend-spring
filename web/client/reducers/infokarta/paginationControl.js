import {
    SET_PAGINATION_NUMBER
} from '../../actions/infokarta/paginationControl';

const paginationControl = (state = {
    pageNumber: {}
}, action) => {
    switch (action.type) {
    case SET_PAGINATION_NUMBER: {
        return {
            ...state,
            pageNumber: action.pageNumber
        };
    }
    default:
        return state;
    }
};

export default paginationControl;
