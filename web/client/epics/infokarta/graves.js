import Rx from "rxjs";
import { get } from 'lodash';

import {
    SEND_SEARCH_REQUEST_FOR_GRAVES,
    SET_SEARCH_PARAMETERS_FOR_GRAVES,
    RESET_SEARCH_PARAMETERS_FOR_GRAVES,
    ZOOM_TO_GRAVE_FROM_GRAVES,
    SET_PAGE_FOR_GRAVES,
    gravesResponseReceived
} from "../../actions/infokarta/graves";

import { clearDetailsAndDocsView } from "../../actions/infokarta/detailsAndDocuments";
import { zoomToPoint, CLICK_ON_MAP } from '../../actions/map';
import { updateAdditionalLayer, removeAdditionalLayer } from '../../actions/additionallayers';
import { SET_CONTROL_PROPERTY, TOGGLE_CONTROL } from '../../actions/controls';
import { defaultIconStyle } from '../../utils/SearchUtils';

import groboviApi from "../../api/infokarta/groboviApi";

// const editModalName = "groboviEdit";

export const sendSearchRequestUponChangeForGraves = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        SEND_SEARCH_REQUEST_FOR_GRAVES,
        SET_SEARCH_PARAMETERS_FOR_GRAVES,
        RESET_SEARCH_PARAMETERS_FOR_GRAVES,
        SET_PAGE_FOR_GRAVES
    ).switchMap(({}) => {
        const searchParameters = get(getState(), "graves.searchParameters");
        const pageNumber = get(getState(), "graves.pageNumber");
        return Rx.Observable.fromPromise(groboviApi.searchGraves(searchParameters, pageNumber)
            .then(data => data))
            .mergeMap((response) => {
                return Rx.Observable.of(
                    gravesResponseReceived(response /* , response.totalSearchMatchCount*/),
                    clearDetailsAndDocsView()
                );
            })
            .catch((error) => {
                return Rx.Observable.of(
                    /* eslint-disable no-console */
                    console.error('error while fetching graves', error)
                );
            });
    });

export const zoomToGraveFromGravesPlugin = (action$) =>
    action$.ofType(ZOOM_TO_GRAVE_FROM_GRAVES)
        .switchMap(({ geom = {} }) => {
            const feature = {
                type: "Feature",
                geometry: {
                    type: "Point",
                    coordinates: geom.coordinates
                }
            };
            return Rx.Observable.from([
                updateAdditionalLayer('graves', 'graves', 'overlay', {
                    features: [feature],
                    type: "vector",
                    name: "gravePoints",
                    id: "gravePoints",
                    visibility: true,
                    style: defaultIconStyle,
                    featuresCrs: "EPSG:3765"
                }),
                zoomToPoint(geom.coordinates, 16, geom.crs.properties.name)
            ]);
        });

export const removeGravePinLayerFromGravesPlugin = (action$) =>
    action$.ofType(TOGGLE_CONTROL, SET_CONTROL_PROPERTY, CLICK_ON_MAP)
        .switchMap(({}) => {
            return Rx.Observable.of(
                removeAdditionalLayer({id: 'graves', owner: 'graves'})
            );
        });
