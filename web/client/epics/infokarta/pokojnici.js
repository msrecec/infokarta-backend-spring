import Rx from "rxjs";

import {
    LOAD_DECEASED,
    EDIT_DECEASED,
    deceasedLoaded
} from "../../actions/infokarta/pokojnici";

import {
    SHOW_INSERT_MODAL,
    generateInsertForm,
    hideDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

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
                .switchMap((responseData) => {
                    console.log('edit response: ', responseData);
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
                        console.error('error while fetching deceased', error)
                    );
                });
        });
