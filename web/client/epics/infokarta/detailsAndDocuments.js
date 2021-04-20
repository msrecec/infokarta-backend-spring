import Rx from "rxjs";
import { get } from 'lodash';

import {
    clearDetailsAndDocsView,
    storeDetailsViewResponse,
    GET_DATA_FOR_DETAILS_VIEW
} from "../../actions/infokarta/detailsAndDocuments";

import {
    insertUnsuccessful
} from "../../actions/infokarta/dynamicComponents";

import { SET_CONTROL_PROPERTY, TOGGLE_CONTROL } from '../../actions/controls';

import pokojniciApi from "../../api/infokarta/pokojniciApi";
import groboviApi from "../../api/infokarta/groboviApi";
import rasvjetaApi from "../../api/infokarta/rasvjetaApi";

export const clearDetailsAndDocsViewOnPluginToggle = (action$) =>
    action$.ofType(SET_CONTROL_PROPERTY, TOGGLE_CONTROL)
        .switchMap(() => {
            return Rx.Observable.of(
                clearDetailsAndDocsView()
            );
        });

export const getEntryDataAndLinkedEntries = (action$, {getState = () => {}} = {}) =>
    action$.ofType(GET_DATA_FOR_DETAILS_VIEW)
        .switchMap(({ fid, fk }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            /* console.log("!!!", activePlugin); */
            switch (activePlugin) {
            case "pokojnici":
                return Rx.Observable.fromPromise(pokojniciApi.getPokojnikAndLinkedGrob(fid, fk)
                    .then(data => data))
                    .mergeMap((response) => {
                        return Rx.Observable.of(
                            storeDetailsViewResponse(response, "pokojnici")
                        );
                    })
                    .catch((error) => {
                        return Rx.Observable.of(
                        /* eslint-disable no-console */
                            console.error('error while fetching deceased', error)
                        );
                    });
            case "grobovi":
                return Rx.Observable.fromPromise(groboviApi.getGrobAndLinkedPokojnici(fid)
                    .then(data => data))
                    .mergeMap((response) => {
                        return Rx.Observable.of(
                            storeDetailsViewResponse(response, "grobovi")
                        );
                    })
                    .catch((error) => {
                        return Rx.Observable.of(
                        /* eslint-disable no-console */
                            console.error('error while fetching deceased', error)
                        );
                    });
            case "rasvjeta":
                return Rx.Observable.fromPromise(rasvjetaApi.getLightingForContainerObject(fid)
                    .then(data => data))
                    .mergeMap((response) => {
                        /* console.log("Epic log from rasvjeta epic", response); */
                        return Rx.Observable.of(
                            storeDetailsViewResponse(response)
                        );
                    })
                    .catch((error) => {
                        return Rx.Observable.of(
                        /* eslint-disable no-console */
                            console.error('error while fetching lighting', error)
                        );
                    });
            default:
                return Rx.Observable.of(
                    insertUnsuccessful("Greška", "Došlo je do greške prilikom dohvaćanja podataka za detaljni pregled.")
                );
            }

        });
