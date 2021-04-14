import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_IMAGES_BY_ENTITY_ID,
    imagesLoadedByEntityId,
    UPLOAD_NEW_IMAGE_BY_ENTITY_ID,
    uploadNewImageResponse
} from "../../actions/infokarta/fileManagement";

import { insertSuccessful, insertUnsuccessful } from "../../actions/infokarta/dynamicModalControl";

import fileManagementApi from "../../api/infokarta/fileManagementApi";

export const loadFileMetadataByEntityId = (action$) =>
    action$.ofType(GET_IMAGES_BY_ENTITY_ID)
        .switchMap(({ entityName, documentType, entityFid }) => {
            return Rx.Observable.fromPromise(fileManagementApi.getMetaByEntityFid(entityName, documentType, entityFid)
                .then(data => data))
                .switchMap((response) => {
                    return Rx.Observable.of(
                        imagesLoadedByEntityId(response.data)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                        /* eslint-disable no-console */
                        console.error('error while fetching entity data', error)
                    );
                });
        });

export const handleImageUploadByEntityId = (action$) =>
    action$.ofType(UPLOAD_NEW_IMAGE_BY_ENTITY_ID)
        .switchMap(({ entityName, documentType, file, entityFid }) => {
            return Rx.Observable.fromPromise(fileManagementApi.uploadFile(entityName, documentType, file, entityFid)
                .then(data => data))
                .mergeMap((response) => {
                    if (response === 200) {
                        return Rx.Observable.of(
                            insertSuccessful("Prijenos uspješan", "Vaš dokument/slika je uspješno pohranjen/a u bazu podataka."),
                            uploadNewImageResponse(response)
                        );
                    } else if (response === 415) {
                        return Rx.Observable.of(
                            insertUnsuccessful("Greška", "Format odabrane datoteke nije podržan."),
                            uploadNewImageResponse(response)
                        );
                    }
                    return Rx.Observable.of(
                        insertUnsuccessful("Greška", "Došlo je do greške prilikom prijenosa. Molimo pokušajte ponovno."),
                        uploadNewImageResponse(response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while fetching entity data', error)
                    );
                });
        });
