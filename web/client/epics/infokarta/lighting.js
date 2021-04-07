import Rx from 'rxjs';
import { get } from 'lodash';

import {
    GET_LIGHTING_DATA,
    lightingDataReceived,
    SET_PAGE_FOR_LIGHTING
} from '../../actions/infokarta/lighting';

import lightingApi from '../../api/infokarta/rasvjetaApi';

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
