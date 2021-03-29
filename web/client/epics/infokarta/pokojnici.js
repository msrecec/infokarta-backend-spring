import Rx from "rxjs";
import { get } from 'lodash';

import {
    LOAD_DECEASED,
    EDIT_DECEASED,
    INSERT_DECEASED,
    ZOOM_TO_GRAVE,
    deceasedLoaded,

    ENABLE_GRAVE_PICK_MODE,
    CONFIRM_GRAVE_PICK,
    showGravePickModal,
    disableGravePickMode
} from "../../actions/infokarta/pokojnici";

import {
    SHOW_INSERT_MODAL,
    generateInsertForm,
    hideEditModal,
    hideInsertModal,
    showInsertModal
} from "../../actions/infokarta/dynamicModalControl";

import { LOAD_FEATURE_INFO } from "../../actions/mapInfo";
import { toggleControl } from '../../actions/controls';
import { zoomAndAddPoint } from '../../actions/search';
import { changeDrawingStatus } from '../../actions/draw';

import pokojniciApi from "../../api/infokarta/pokojniciApi";

export const fetchDataForTable = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED)
        .switchMap(({ searchParameters = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters)
                .then(data => data))
                .switchMap((deceased) => {
                    return Rx.Observable.of(
                        deceasedLoaded(deceased.pokojnici, deceased.totalSearchMatchCount)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching deceased', error)
                    );
                });
        });

export const sendEditData = (action$ /* , store*/) =>
    action$.ofType(EDIT_DECEASED)
        .switchMap(({ itemToEdit = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.editPokojnik(itemToEdit)
                .then(data => data))
                .switchMap((response) => {
                    console.log('edit response: ', response);
                    return Rx.Observable.of(
                        hideEditModal()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while editing deceased', error)
                    );
                });
        });

export const fetchColumnsForInsert = (action$ /* , store*/) =>
    action$.ofType(SHOW_INSERT_MODAL)
        .switchMap(({}) => {
            // const state = store.getState();
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

export const insertNew = (action$, {getState = () => {}} = {}) =>
    action$.ofType(INSERT_DECEASED)
        .switchMap(({ itemToInsert = {} }) => {
            let pokojniciStore = get(getState(), "pokojnici");
            let temp = pokojniciStore.chosenGrave;
            return Rx.Observable.fromPromise(pokojniciApi.insertPokojnik(itemToInsert, temp)
                .then(data => data))
                .switchMap((response) => {
                    console.log('insert response: ', response);
                    return Rx.Observable.of(
                        hideInsertModal()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while inserting new deceased', error)
                    );
                });
        });

export const zoomToGrave = (action$ /* , store*/) =>
    action$.ofType(ZOOM_TO_GRAVE)
        .switchMap(({ graveId = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.getGraveCoordinates(graveId)
                .then(data => data))
                .mergeMap((res) => {
                    console.log('zoom response: ', res);
                    return Rx.Observable.of(
                        changeDrawingStatus("clean", "", "drawer"),
                        zoomAndAddPoint(res.coordinates, res.zoom, res.crs)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching geom for zoom function', error)
                    );
                });
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
            let pokojniciStore = get(getState(), "pokojnici");
            if (pokojniciStore.graveChooseEnabled === true) {
                if (data.numberReturned === 1 && data.features[0].id.includes("Grobovi")) {
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
