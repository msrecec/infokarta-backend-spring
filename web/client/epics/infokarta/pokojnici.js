import Rx from "rxjs";

import {
    LOAD_DECEASED,
    EDIT_DECEASED,
    INSERT_DECEASED,
    ZOOM_TO_GRAVE,
    deceasedLoaded
} from "../../actions/infokarta/pokojnici";

import {
    SHOW_INSERT_MODAL,
    generateInsertForm,
    hideDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

import {
    panTo
} from '../../actions/map';

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
                        hideDynamicModal()
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

export const insertNew = (action$ /* , store*/) =>
    action$.ofType(INSERT_DECEASED)
        .switchMap(({ itemToInsert = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.insertPokojnik(itemToInsert)
                .then(data => data))
                .switchMap((response) => {
                    console.log('insert response: ', response);
                    return Rx.Observable.of(
                        hideDynamicModal()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching columns to insert new deceased', error)
                    );
                });
        });

export const zoomToGrave = (action$ /* , store*/) =>
    action$.ofType(ZOOM_TO_GRAVE)
        .switchMap(({ graveId = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.getGraveCoordinates(graveId)
                .then(data => data))
                .switchMap((response) => {
                    console.log('grave response: ', response);
                    return Rx.Observable.of(
                        panTo(response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching columns to insert new deceased', error)
                    );
                });
        });
