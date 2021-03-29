import {
    DECEASED_LOADED,
    SHOW_GRAVE_PICK_MODAL,
    HIDE_GRAVE_PICK_MODAL,
    ENABLE_GRAVE_PICK_MODE,
    DISABLE_GRAVE_PICK_MODE,
    CONFIRM_GRAVE_PICK
} from "../../actions/infokarta/pokojnici";
const pokojnici = (
    state = {
        deceased: [],
        pageNumber: [],
        graveChooseEnabled: false,
        graveChooseMode: "initial", // "initial", "single", "multiple"
        chooseGraveModal: false,
        chosenGrave: null,
        graveData: {}
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
    case ENABLE_GRAVE_PICK_MODE: {
        return {
            ...state,
            graveChooseEnabled: true,
            chooseGraveModal: false,
            graveChooseMode: "initial",
            chosenGrave: null
        };
    } // dok je graveChooseEnabled istinit, klik na grob savea njegov id u state i prikazuje ga u modalu
    case DISABLE_GRAVE_PICK_MODE: {
        return {
            ...state,
            graveChooseEnabled: false,
            chooseGraveModal: false,
            chosenGrave: null
        };
    } // ugasi ovaj mode i sve vezano uz njega. id groba se dodaje u fk prilikon slanja http zahtjeva
    case SHOW_GRAVE_PICK_MODAL: {
        return {
            ...state,
            chooseGraveModal: true,
            graveChooseMode: action.mode,
            chosenGrave: action.graveId,
            graveData: action.grave
        };
    } // ako je graveChooseEnabled istinit, kliknut jedan feature i taj feature je grob ucitaj ga u modal
    case HIDE_GRAVE_PICK_MODAL: {
        return {
            ...state,
            chooseGraveModal: false,
            graveChooseMode: "initial",
            chosenGrave: null
        };
    } // kliknut je botun za odustajanje na modalu koji prikazuje grob
    case CONFIRM_GRAVE_PICK: {
        return {
            ...state,
            graveChooseEnabled: false,
            chooseGraveModal: false,
            graveChooseMode: "initial"
        };
    } // korisnik je zadovoljan odabirom groba, gasi se modal za prikaz i pali se insert modal
    default:
        return state;
    }
};

export default pokojnici;
