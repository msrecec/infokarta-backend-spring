import Rx from 'rxjs';
import { get } from 'lodash';

import {
    GET_LIGHTING_DATA,
    lightingDataReceived,
    SET_PAGE_FOR_LIGHTING,
    ZOOM_TO_LAMP_FROM_LIGHTING,
    EDIT_LIGHTING
} from '../../actions/infokarta/lighting';

import lightingApi from '../../api/infokarta/rasvjetaApi';
import { updateAdditionalLayer, removeAdditionalLayer } from '../../actions/additionallayers';
import { zoomToPoint, CLICK_ON_MAP } from '../../actions/map';
import { SET_CONTROL_PROPERTY, toggleControl, TOGGLE_CONTROL } from '../../actions/controls';

import { defaultIconStyle } from '../../utils/SearchUtils';

import {
    SHOW_INSERT_MODAL,
    generateInsertForm,
    hideEditModal,
    hideInsertModal,
    showInsertModal,
    clearDynamicComponentStore,
    insertSuccessful,
    insertUnsuccessful,
    hideInsertConfirmationModal
} from "../../actions/infokarta/dynamicModalControl";


export const getLightingAppropriateData = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        GET_LIGHTING_DATA,
        SET_PAGE_FOR_LIGHTING)
        .switchMap(({}) => {
            const pageNumber = get(getState(), "lighting.pageNumber");
            return Rx.Observable.fromPromise(lightingApi.getLightingData(pageNumber)
                .then(data => data))
                .switchMap((response) => {
                    return Rx.Observable.of(
                        lightingDataReceived(response)
                    );
                });
        });

export const zoomToLampFromRasvjetaPlugin = (action$) =>
    action$.ofType(ZOOM_TO_LAMP_FROM_LIGHTING)
        .switchMap(({ geom = {} }) => {
            console.log("geom console log: ", geom);
            const feature = {
                type: "Feature",
                geometry: {
                    type: "Point",
                    coordinates: [geom.coordinates[0], geom.coordinates[1]]
                }
            };
            return Rx.Observable.from([
                updateAdditionalLayer('lighting', 'lighting', 'overlay', {
                    features: [feature],
                    type: "vector",
                    name: "lightingPoints",
                    id: "lightingPoints",
                    visibility: true,
                    style: defaultIconStyle,
                    featuresCrs: "EPSG:3765"
                }),
                zoomToPoint(geom.coordinates, 16, geom.crs.properties.name)
            ]);
        });

export const removeLightingPinLayer = (action$) =>
    action$.ofType(TOGGLE_CONTROL, SET_CONTROL_PROPERTY, CLICK_ON_MAP)
        .switchMap(({}) => {
            return Rx.Observable.of(
                removeAdditionalLayer({id: 'lighting', owner: 'lighting'})
            );
        });

export const sendEditDataForLighting = (action$) =>
    action$.ofType(EDIT_LIGHTING)
        .switchMap(({ itemToEdit }) => {
            return Rx.Observable.fromPromise(lightingApi.editLightingData(itemToEdit)
                .then(data => data))
                .mergeMap((response) => {
                    console.log('lampa edit', response);
                    return Rx.Observable.of(
                        hideEditModal()
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while editing deceased', error)
                    );
                });
        });
