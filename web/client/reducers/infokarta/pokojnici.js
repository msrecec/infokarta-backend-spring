import { DECEASED_LOADED } from "../../actions/infokarta/pokojnici";
import { DECEASED_LOADED_BY_PAGE } from "../../actions/infokarta/pokojnici";
const pokojnici = (
    state = {
        deceased: [],
        pageNumber: [],
    },
    action
) => {
    switch (action.type) {
        case DECEASED_LOADED: {
            // console.log('reducer1');
            return {
                ...state,
                deceased: action.deceased,
            };
        }
        case DECEASED_LOADED_BY_PAGE: {
            return {
                ...state,
                pageNumber: action.pageNumber,
            };
        }
        default:
            // console.log('reducer2');
            return state;
    }
};

export default pokojnici;
