import { DECEASED_LOADED } from "../../actions/infokarta/pokojnici";
const pokojnici = (
    state = {
        deceased: [],
        pageNumber: []
    },
    action
) => {
    switch (action.type) {
    case DECEASED_LOADED: {
        return {
            ...state,
            deceased: action.deceased,
            totalNumber: action.totalNumber
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
