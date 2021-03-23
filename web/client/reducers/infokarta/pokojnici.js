import {
    DECEASED_LOADED,
    START_CHOOSE_GRAVE_MODE,
    END_CHOOSE_GRAVE_MODE
} from "../../actions/infokarta/pokojnici";
const pokojnici = (
    state = {
        deceased: [],
        pageNumber: [],
        chooseGraveMode: false,
        chosenGrave: {}
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
    case START_CHOOSE_GRAVE_MODE: {
        return {
            ...state,
            chooseGraveMode: true
        };
    }
    case END_CHOOSE_GRAVE_MODE: {
        return {
            ...state,
            chooseGraveMode: false,
            chosenGrave: action.chosenGrave
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
