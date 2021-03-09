import Rx from 'rxjs';

import {
    LOAD_DECEASED,
    deceasedLoaded
} from '../../actions/infokarta/pokojnici';

import pokojniciApi from '../../api/infokarta/pokojniciApi';

const fieldsToExclude = ["ogc_fid", "field_10"];
const readOnlyFields = ["fid", "fk"];

export const fetchDataForTable = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED)
        .switchMap(({ searchParameters = {} }) => {
            console.log('epic: ', searchParameters);
            // const state = store.getState();
            return Rx.Observable.fromPromise(pokojniciApi.searchPokojnici(searchParameters)
                .then(data => data))
                .switchMap((deceased) => {
                    return Rx.Observable.of(
                        deceasedLoaded(deceased, fieldsToExclude, readOnlyFields)
                    );
                })
                .catch(() => {
                    return Rx.Observable.of(
                        console.error('error while fetching deceased')
                        // TODO proucit basicError npr. /epics/details.js, /utils/NotificationUtils
                    );
                });
        });

