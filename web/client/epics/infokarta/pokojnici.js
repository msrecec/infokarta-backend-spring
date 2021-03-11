import Rx from 'rxjs';

import {
    LOAD_DECEASED,
    deceasedLoaded
} from '../../actions/infokarta/pokojnici';

import pokojniciApi from '../../api/infokarta/pokojniciApi';

const fieldsToExclude = ["ogc_fid", "field_10"]; // TODO maknit nekako u parent plugin
const readOnlyFields = ["fid", "fk"];

export const fetchDataForTable = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED)
        .switchMap(({ searchParameters = {} }) => {
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters)
                .then(data => data.pokojnici))
                .switchMap((deceased) => {
                    return Rx.Observable.of(
                        deceasedLoaded(deceased, fieldsToExclude, readOnlyFields)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching deceased', error)
                    );
                });
        });
