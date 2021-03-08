import Rx from "rxjs";

import {
    LOAD_DECEASED,
    deceasedLoaded,
    LOAD_DECEASED_BY_PAGE,
    deceasedLoadedByPage
} from "../../actions/infokarta/pokojnici";

import pokojniciApi from "../../api/infokarta/pokojniciApi";

export const fetchDataForTable = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED).switchMap(() => {
        return Rx.Observable.fromPromise(
            pokojniciApi.getPokojnici().then((data) => data)
        )
            .switchMap((deceased) => {
                return Rx.Observable.of(deceasedLoaded(deceased));
            })
            .catch(() => {
                return Rx.Observable.of(
                    console.error("error while fetching deceased")
                    // TODO proucit basicError npr. /epics/details.js, /utils/NotificationUtils
                );
            });
    });

export const fetchDataForTableByPage = (action$ /* , store*/) =>
    action$.ofType(LOAD_DECEASED_BY_PAGE).switchMap(({ pageNumber = {} }) => {
        console.log('pagenum epic: ', pageNumber);
        // const state = store.getState();
        return Rx.Observable.fromPromise(
            pokojniciApi.getPokojniciByPage(pageNumber).then((data) => data)
        )
            .switchMap((deceased) => {
                return Rx.Observable.of(deceasedLoadedByPage(deceased, pageNumber));
            })
            .catch(() => {
                return Rx.Observable.of(
                    console.error("error while fetching deceased by page")
                );
            });
    });
