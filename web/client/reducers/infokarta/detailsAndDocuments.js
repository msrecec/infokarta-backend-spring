import {
    STORE_DETAILS_VIEW_RESPONSE,
    CLEAR_DETAILS_AND_DOCS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";
const detailsAndDocuments = (
    state = {},
    action
) => {
    switch (action.type) {
    case STORE_DETAILS_VIEW_RESPONSE: {
        console.log('store details view response', action.pluginName, action.response);
        const tempBoolName = action.pluginName + "Show";
        const tempItemName = action.pluginName + "Item";
        return {
            ...state,
            [tempBoolName]: true,
            [tempItemName]: action.response
        };
    }
    case CLEAR_DETAILS_AND_DOCS_VIEW: {
        console.log('clear action', state);
        let newState = {};
        Object.keys(state).map((variableName) => {
            if (variableName.includes("Show")) {
                newState[variableName] = false;
            } else {
                newState[variableName] = {};
            }
        });
        console.log('after action', newState);
        return newState;
    }
    default:
        return state;
    }
};

export default detailsAndDocuments;
