import { DECEASED_LOADED, DECEASED_LOADED_BY_PAGE } from "../../actions/infokarta/pokojnici";
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
            deceased: action.deceased
        };
    }
    case DECEASED_LOADED_BY_PAGE: {
        return {
            ...state,
            deceased: action.deceased
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
