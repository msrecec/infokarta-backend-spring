import Rx from 'rxjs';

import {
    GET_LIGHTING_DATA,
    lightingDataReceived
} from '../../actions/infokarta/lighting';

import lightingApi from '../../api/infokarta/rasvjetaApi';

export const getLightingAppropriateData = (action$/* , store */) =>
    action$.ofType(GET_LIGHTING_DATA)
        .switchMap(({}) => {
            /*             const state = store.getState(); */
            return Rx.Observable.fromPromise(lightingApi.getLightingData()
                .then(data => data))
                .switchMap((lighting) => {
                    return Rx.Observable.of(
                        lightingDataReceived(lighting)
                    );
                });
        });
