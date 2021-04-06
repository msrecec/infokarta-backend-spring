import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_FILES_BY_ENTITY_ID,
    filesLoadedByEntityId
} from "../../actions/infokarta/fileManagement";

import Api from "../../api/infokarta/uploadApi";

export const loadFilesByEntityId = (action$) =>
    action$.ofType(GET_FILES_BY_ENTITY_ID)
        .switchMap(({ entityFid = {} }) => {
            return Rx.Observable.fromPromise(Api.getMetaByEntityFid(entityFid)
                .then(data => data))
                .switchMap((response) => {
                    return Rx.Observable.of(
                        filesLoadedByEntityId(response.data)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching entity data', error)
                    );
                });
        });
