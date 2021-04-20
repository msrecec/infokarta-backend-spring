import Rx from "rxjs";
import { get } from 'lodash';

import {
    SEND_SEARCH_REQUEST_FOR_DECEASED,
    SET_SEARCH_PARAMETERS_FOR_DECEASED,
    RESET_SEARCH_PARAMETERS_FOR_DECEASED,
    SET_PAGE_FOR_DECEASED,
    // EDIT_DECEASED,
    INSERT_DECEASED,
    ZOOM_TO_GRAVE_FROM_DECEASED,
    deceasedResponseReceived,
    sendSearchRequestForDeceased
} from "../../actions/infokarta/deceased";

import {
    ENABLE_GRAVE_PICK_MODE,
    CONFIRM_GRAVE_PICK,
    showGravePickModal,
    disableGravePickMode,
    clearGravePickerToolStore
} from "../../actions/infokarta/gravePickerTool";

import {
    GET_COLUMNS_FOR_INSERT_FROM_DATABASE,
    clearDynamicComponentStore,
    insertSuccessful,
    insertUnsuccessful,
    showDynamicModal,
    hideDynamicModal,
    alternateModalVisibility,
    acquireCurrentPluginName
} from "../../actions/infokarta/dynamicComponents";

import { clearDetailsAndDocsView } from "../../actions/infokarta/detailsAndDocuments";

import { LOAD_FEATURE_INFO } from "../../actions/mapInfo";
import { SET_CONTROL_PROPERTY, toggleControl, TOGGLE_CONTROL } from '../../actions/controls';
import { zoomToPoint, CLICK_ON_MAP } from '../../actions/map';
import { updateAdditionalLayer, removeAdditionalLayer } from '../../actions/additionallayers';

import { defaultIconStyle } from '../../utils/SearchUtils';

import pokojniciApi from "../../api/infokarta/pokojniciApi";
// import deceased from "../../reducers/infokarta/deceased";

const insertModalName = "pokojniciInsert";
// const insertConfirmationModalName = "pokojniciConfirmation";
// const editModalName = "pokojniciEdit";

export const sendSearchRequestUponChangeForDeceased = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        SEND_SEARCH_REQUEST_FOR_DECEASED,
        SET_SEARCH_PARAMETERS_FOR_DECEASED,
        RESET_SEARCH_PARAMETERS_FOR_DECEASED,
        SET_PAGE_FOR_DECEASED
    ).switchMap(({}) => {
        const searchParameters = get(getState(), "deceased.searchParameters");
        const pageNumber = get(getState(), "deceased.pageNumber");
        return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters, pageNumber)
            .then(data => data))
            .mergeMap((response) => {
                return Rx.Observable.of(
                    deceasedResponseReceived(response.pokojnici, response.totalSearchMatchCount)
                    // clearDetailsAndDocsView()
                );
            })
            .catch((error) => {
                return Rx.Observable.of(
                    /* eslint-disable no-console */
                    console.error('error while fetching deceased', error)
                );
            });
    });

export const zoomToGraveFromPokojniciPlugin = (action$) =>
    action$.ofType(ZOOM_TO_GRAVE_FROM_DECEASED)
        .switchMap(({ graveId = {} }) => {
            return Rx.Observable.fromPromise(pokojniciApi.getGraveCoordinates(graveId)
                .then(data => data))
                .switchMap((res) => {
                    const feature = {
                        type: "Feature",
                        geometry: {
                            type: "Point",
                            coordinates: [res.coordinates.x, res.coordinates.y]
                        }
                    };
                    /* console.log('zoom response: ', res); */
                    return Rx.Observable.from([
                        updateAdditionalLayer('graves', 'graves', 'overlay', {
                            features: [feature],
                            type: "vector",
                            name: "gravePoints",
                            id: "gravePoints",
                            visibility: true,
                            style: defaultIconStyle,
                            featuresCrs: "EPSG:3765"
                        }),
                        zoomToPoint(res.coordinates, res.zoom, res.crs)
                    ]);
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching geom for zoom function', error)
                    );
                });
        });

export const removeGravePinLayerFromDeceasedPlugin = (action$) =>
    action$.ofType(TOGGLE_CONTROL, SET_CONTROL_PROPERTY, CLICK_ON_MAP)
        .switchMap(({}) => {
            return Rx.Observable.of(
                removeAdditionalLayer({id: 'graves', owner: 'graves'})
            );
        });

export const startChooseGraveMode = (action$) =>
    action$.ofType(ENABLE_GRAVE_PICK_MODE)
        .mergeMap(({}) => {
            return Rx.Observable.of(
                hideDynamicModal(),
                toggleControl("drawer")
            );
        });

export const confirmGravePickAndOpenInsertModal = (action$) =>
    action$.ofType(CONFIRM_GRAVE_PICK)
        .switchMap(({}) => {
            return Rx.Observable.of(
                showDynamicModal(insertModalName)
            );
        });

export const loadGraveDataIntoInsertForm = (action$, {getState = () => {}} = {}) =>
    action$.ofType(LOAD_FEATURE_INFO)
        .switchMap(({ data = {} }) => {
            let gravePickerToolStore = get(getState(), "gravePickerTool");
            if (gravePickerToolStore.graveChooseEnabled === true) {
                if (data.numberReturned === 1 && data.features[0].id.toUpperCase().includes("GROBOVI")) {
                    let temp = data.features[0].id.split(".");
                    const id = parseInt(temp[1], 10);
                    return Rx.Observable.of(
                        showGravePickModal("single", id, data.features[0].properties)
                    );
                }
                return Rx.Observable.of(
                    showGravePickModal("multiple")
                );
            }
            return Rx.Observable.of(
                disableGravePickMode()
                // workaround da ne baca errore bezveze
            );
        });

export const insertNewDeceased = (action$, {getState = () => {}} = {}) =>
    action$.ofType(INSERT_DECEASED)
        .switchMap(({ itemToInsert = {} }) => {
            let gravePickerToolStore = get(getState(), "gravePickerTool");
            let temp = gravePickerToolStore.chosenGrave;
            return Rx.Observable.fromPromise(pokojniciApi.insertPokojnik(itemToInsert, temp)
                .then(data => data))
                .mergeMap((response) => {
                    if (response.status === 200) {
                        console.log('insert response: ', response.data);
                        return Rx.Observable.of(
                            sendSearchRequestForDeceased(),
                            clearDynamicComponentStore(),
                            clearGravePickerToolStore(),
                            insertSuccessful("Unos potvrđen", "Nova stavka je uspješno pohranjena u bazu podataka.")
                        );
                    }
                    return Rx.Observable.of(
                        alternateModalVisibility("pokojniciConfirmation", "pokojniciInsert"),
                        insertUnsuccessful("Došlo je do greške", "Nova stavka nije pohranjena u bazu. Error: " + response.status)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while inserting new deceased', error)
                    );
                });
        });
