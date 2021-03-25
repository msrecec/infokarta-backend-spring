import {
    DECEASED_LOADED,
    ENABLE_GRAVE_PICK_MODAL,
    DISABLE_GRAVE_PICK_MODAL,
    SET_GRAVE_PICK_MODE
} from "../../actions/infokarta/pokojnici";
const pokojnici = (
    state = {
        deceased: [],
        pageNumber: [],
        chooseGraveModal: false,
        graveChooseMode: "initial", // "initial", "single", "multiple"
        chosenGrave: null
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
    case ENABLE_GRAVE_PICK_MODAL: {
        return {
            ...state,
            chooseGraveModal: true
        };
    }
    case DISABLE_GRAVE_PICK_MODAL: {
        return {
            ...state,
            chooseGraveModal: false
        };
    }
    case SET_GRAVE_PICK_MODE: {
        return {
            ...state,
            graveChooseMode: action.mode,
            chosenGrave: action.grave
        };
    }
    default:
        return state;
    }
};

export default pokojnici;
