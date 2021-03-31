import {
    DECEASED_LOADED
} from "../../actions/infokarta/deceased";
const deceased = (
    state = {
        data: [],
        totalNumber: null
    },
    action
) => {
    switch (action.type) {
    case DECEASED_LOADED: {
        return {
            ...state,
            data: action.deceased,
            totalNumber: action.totalNumber
        };
    }
    default:
        return state;
    }
};

export default deceased;
