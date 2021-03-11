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
            totalNumber: action.totalNumber,
            fieldsToExclude: action.exclude,
            readOnlyFields: action.readonly
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
