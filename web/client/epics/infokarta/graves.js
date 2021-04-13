import Rx from "rxjs";
import { get } from 'lodash';

import {
    SEND_SEARCH_REQUEST_FOR_GRAVES,
    SET_SEARCH_PARAMETERS_FOR_GRAVES,
    RESET_SEARCH_PARAMETERS_FOR_GRAVES,
    // SET_PAGE_FOR_GRAVES,
    gravesResponseReceived
} from "../../actions/infokarta/graves";

import { closeDetailsAndDocsView } from "../../actions/infokarta/detailsAndDocuments";

import groboviApi from "../../api/infokarta/groboviApi";

const editModalName = "groboviEdit";

export const sendSearchRequestUponUponChangeForGraves = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        SEND_SEARCH_REQUEST_FOR_GRAVES,
        SET_SEARCH_PARAMETERS_FOR_GRAVES,
        RESET_SEARCH_PARAMETERS_FOR_GRAVES
        // SET_PAGE_FOR_GRAVES
    ).switchMap(({}) => {
        const searchParameters = get(getState(), "deceased.searchParameters");
        const pageNumber = get(getState(), "deceased.pageNumber");
        return Rx.Observable.fromPromise(groboviApi.searchGraves(searchParameters, pageNumber)
            .then(data => data))
            .mergeMap((response) => {
                return Rx.Observable.of(
                    gravesResponseReceived(response /* , response.totalSearchMatchCount*/),
                    closeDetailsAndDocsView()
                );
            })
            .catch((error) => {
                return Rx.Observable.of(
                    /* eslint-disable no-console */
                    console.error('error while fetching deceased', error)
                );
            });
    });
