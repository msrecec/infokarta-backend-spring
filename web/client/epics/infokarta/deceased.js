import Rx from "rxjs";
import { get } from 'lodash';

import {
    SEND_SEARCH_REQUEST_FOR_DECEASED,
    SET_SEARCH_PARAMETERS_FOR_DECEASED,
    RESET_SEARCH_PARAMETERS_FOR_DECEASED,
    SET_PAGE_FOR_DECEASED,
    EDIT_DECEASED,
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
    SHOW_INSERT_MODAL,
    generateInsertForm,
    hideEditModal,
    hideInsertModal,
    showInsertModal,
    clearAllDynamicForms
} from "../../actions/infokarta/dynamicModalControl";

import { LOAD_FEATURE_INFO } from "../../actions/mapInfo";
import { toggleControl, TOGGLE_CONTROL } from '../../actions/controls';
import { zoomToPoint } from '../../actions/map';
import { updateAdditionalLayer, removeAdditionalLayer } from '../../actions/additionallayers';
import { defaultIconStyle } from '../../utils/SearchUtils';

import pokojniciApi from "../../api/infokarta/pokojniciApi";

export const sendSearchRequestUponSearchParameterOrPageChange = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        SEND_SEARCH_REQUEST_FOR_DECEASED,
        SET_SEARCH_PARAMETERS_FOR_DECEASED,
        RESET_SEARCH_PARAMETERS_FOR_DECEASED,
        SET_PAGE_FOR_DECEASED
    ).switchMap(({}) => {
        const searchParameters = get(getState(), "deceased.searchParameters");
        const pageNumber = get(getState(), "deceased.pageNumber");
        console.log('!!! sendSearchRequestUponSearchParameterOrPageChange', searchParameters, pageNumber);
        return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters, pageNumber)
            .then(data => data))
            .switchMap((response) => {
                return Rx.Observable.of(
                    deceasedResponseReceived(response.pokojnici, response.totalSearchMatchCount)
                );
            })
            .catch((error) => {
                return Rx.Observable.of(
                    /* eslint-disable no-console */
                    console.error('error while fetching deceased', error)
                );
            });
    });

export const sendEditDataForDeceased = (action$) =>
    action$.ofType(EDIT_DECEASED)
        .switchMap(({ itemToEdit = {} }) => {
            return Rx.Observable.fromPromise(pokojniciApi.editPokojnik(itemToEdit)
                .then(data => data))
                .mergeMap((response) => {
                    console.log('!!! edit response: ', response);
                    return Rx.Observable.of(
                        hideEditModal(),
                        sendSearchRequestForDeceased()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while editing deceased', error)
                    );
                });
        });

export const fetchColumnsFromDeceasedForInsert = (action$) =>
    action$.ofType(SHOW_INSERT_MODAL)
        .switchMap(({}) => {
            return Rx.Observable.fromPromise(pokojniciApi.getPokojniciColumns()
                .then(data => data))
                .switchMap((columns) => {
                    return Rx.Observable.of(
                        generateInsertForm(columns)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching columns to insert new deceased', error)
                    );
                });
        });

export const insertNewDeceased = (action$, {getState = () => {}} = {}) =>
    action$.ofType(INSERT_DECEASED)
        .switchMap(({ itemToInsert = {} }) => {
            let gravePickerToolStore = get(getState(), "gravePickerTool");
            let temp = gravePickerToolStore.chosenGrave;
            return Rx.Observable.fromPromise(pokojniciApi.insertPokojnik(itemToInsert, temp)
                .then(data => data))
                .mergeMap((response) => {
                    console.log('!!! insert response: ', response);
                    return Rx.Observable.of(
                        clearAllDynamicForms(),
                        clearGravePickerToolStore()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while inserting new deceased', error)
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
                    console.log('!!! zoom response: ', res);
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

export const removeGravePinLayer = (action$) =>
    action$.ofType(TOGGLE_CONTROL)
        .switchMap(({}) => {
            return Rx.Observable.of(
                removeAdditionalLayer({id: 'graves', owner: 'graves'})
            );
        });

export const startChooseGraveMode = (action$) =>
    action$.ofType(ENABLE_GRAVE_PICK_MODE)
        .mergeMap(({}) => {
            return Rx.Observable.of(
                hideInsertModal(),
                toggleControl("drawer")
            );
        });

export const confirmGravePickAndOpenInsertModal = (action$) =>
    action$.ofType(CONFIRM_GRAVE_PICK)
        .switchMap(({}) => {
            return Rx.Observable.of(
                showInsertModal()
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
