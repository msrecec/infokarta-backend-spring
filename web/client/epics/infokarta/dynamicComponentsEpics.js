import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_COLUMNS_FOR_INSERT_FROM_DATABASE,
    GET_ITEM_FOR_EDIT_FROM_DATABASE,
    SAVE_EDITED_ITEM,
    clearDynamicComponentStore,
    insertSuccessful,
    insertUnsuccessful,
    showDynamicModal,
    hideDynamicModal,
    alternateModalVisibility,
    acquireCurrentPluginName,
    clearActivePlugin,
    ZOOM_TO_ACTIVE_PLUGIN_SEGMENT
} from "../../actions/infokarta/dynamicComponents";

import {
    SET_CONTROL_PROPERTY,
    TOGGLE_CONTROL
} from "../../actions/controls";

import dynamicComponentsApi from "../../api/infokarta/dynamicComponentsApi";
import pokojniciApi from "../../api/infokarta/pokojniciApi";
import { updateAdditionalLayer } from "../../actions/additionallayers";
import { defaultIconStyle } from '../../utils/SearchUtils';
import { zoomToPoint } from '../../actions/map';


export const fetchEditDataAndSendToModal = (action$, {getState = () => {}} = {}) =>
    action$.ofType(GET_ITEM_FOR_EDIT_FROM_DATABASE)
        .switchMap(({ fid = {} }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            /* console.log('edit', activePlugin, fid); */
            return Rx.Observable.fromPromise(dynamicComponentsApi.getItem(activePlugin, fid)
                .then(data => data))
                .switchMap((response) => {
                    return Rx.Observable.of(
                        showDynamicModal("Edit", response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('Error while getting data for edit:', error)
                    );
                });
        });

export const fetchColumnsFromDeceasedForInsert = (action$, {getState = () => {}} = {}) =>
    action$.ofType(GET_COLUMNS_FOR_INSERT_FROM_DATABASE)
        .switchMap(({}) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(dynamicComponentsApi.getColumns(activePlugin)
                .then(data => data))
                .switchMap((columns) => {
                    return Rx.Observable.of(
                        showDynamicModal("Insert", columns)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('Error while getting columns for insert:', error)
                    );
                });
        });

export const saveEditedItemToDatabase = (action$, {getState = () => {}} = {}) =>
    action$.ofType(SAVE_EDITED_ITEM)
        .switchMap(({ item = {} }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(dynamicComponentsApi.saveEdit(activePlugin, item)
                .then(data => data))
                .mergeMap((response) => {
                    return Rx.Observable.of(
                        clearDynamicComponentStore(),
                        insertSuccessful("Unos potvrđen", "Vaša izmjena je uspješno pohranjena u bazu podataka.")
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('Error while getting columns for insert:', error)
                    );
                });
        });

export const clearActivePluginOnDrawerMenuClose = (action$) =>
    action$.ofType(TOGGLE_CONTROL)
        .switchMap(({}) => {
            return Rx.Observable.of(
                clearActivePlugin()
            );
        });


export const executeZoomToActivePluginSegment  = (action$, {getState = () => {}} = {}) =>
    action$.ofType(ZOOM_TO_ACTIVE_PLUGIN_SEGMENT)
        .switchMap(({ geom = {} }) => {
            const activePlugin = get(getState(), "dynamicComponents.activePlugin");
            const feature = {
                type: "Feature",
                geometry: {
                    type: "Point",
                    coordinates: geom.coordinates
                }
            };
            return Rx.Observable.from([
                updateAdditionalLayer(activePlugin, activePlugin, 'overlay', {
                    features: [feature],
                    type: "vector",
                    name: `${activePlugin}Points`,
                    id: `${activePlugin}Points`,
                    visibility: true,
                    style: defaultIconStyle,
                    featuresCrs: "EPSG:3765"
                }),
                zoomToPoint(geom.coordinates, 16, geom.crs.properties.name)
            ]);
        }
        );
// export const clearActivePluginOnDrawerMenuClose = (action$) =>
//     action$.ofType(TOGGLE_CONTROL)
//         .switchMap(({}) => {
//             return Rx.Observable.of(
//                 clearActivePlugin()
//             );
//         });
