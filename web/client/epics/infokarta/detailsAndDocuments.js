import Rx from "rxjs";

import {
    clearDetailsAndDocsView,
    storeDetailsViewResponse,
    GET_DATA_FOR_DETAILS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";

import { SET_CONTROL_PROPERTY, TOGGLE_CONTROL } from '../../actions/controls';

import groboviApi from "../../api/infokarta/groboviApi";

export const clearDetailsAndDocsViewOnPluginToggle = (action$) =>
    action$.ofType(SET_CONTROL_PROPERTY, TOGGLE_CONTROL)
        .switchMap(() => {
            return Rx.Observable.of(
                clearDetailsAndDocsView()
            );
        });

export const getEntryDataAndLinkedEntries = (action$) =>
    action$.ofType(GET_DATA_FOR_DETAILS_VIEW)
        .switchMap(({ fid, fk }) => {
            return Rx.Observable.fromPromise(groboviApi.getGrobAndLinkedPokojnici(fid)
                .then(data => data))
                .mergeMap((response) => {
                    return Rx.Observable.of(
                        storeDetailsViewResponse(response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while fetching deceased', error)
                    );
                });
        });
