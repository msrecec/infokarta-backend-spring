import Rx from "rxjs";

import {
    LOAD_DECEASED,
    EDIT_DECEASED,
    deceasedLoaded
} from "../../actions/infokarta/pokojnici";

import {
    hideDynamicModal
} from "../../actions/infokarta/dynamicModalControl";

import pokojniciApi from "../../api/infokarta/pokojniciApi";

const fieldsToExclude = ["ogc_fid", "field_10"]; // TODO maknit nekako u parent plugin
const readOnlyFields = ["fid", "fk"];

export const fetchDataForTable = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED)
        .switchMap(({ searchParameters = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters)
                .then(data => data))
                .switchMap((deceased) => {
                    return Rx.Observable.of(
                        deceasedLoaded(deceased.pokojnici, deceased.totalSearchMatchCount, fieldsToExclude, readOnlyFields)
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
