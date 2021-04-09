import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_FILES_BY_ENTITY_ID,
    UPLOAD_NEW_FILE_BY_ENTITY_ID,
    filesLoadedByEntityId,
    uploadNewFileResponse
} from "../../actions/infokarta/fileManagement";

import { insertSuccessful, insertUnsuccessful } from "../../actions/infokarta/dynamicModalControl";

import fileManagementApi from "../../api/infokarta/fileManagementApi";

export const loadFileMetadataByEntityId = (action$) =>
    action$.ofType(GET_FILES_BY_ENTITY_ID)
        .switchMap(({ entityName, documentType, entityFid }) => {
            return Rx.Observable.fromPromise(fileManagementApi.getMetaByEntityFid(entityName, documentType, entityFid)
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

export const handleImageUploadByEntityId = (action$) =>
    action$.ofType(UPLOAD_NEW_FILE_BY_ENTITY_ID)
        .switchMap(({ entityName, documentType, file, entityFid }) => {
            return Rx.Observable.fromPromise(fileManagementApi.uploadFile(entityName, documentType, file, entityFid)
                .then(data => data))
                .mergeMap((response) => {
                    if (response === 200) {
                        return Rx.Observable.of(
                            insertSuccessful("Prijenos uspješan", "Vaš dokument/slika je uspješno pohranjen/a u bazu podataka."),
                            uploadNewFileResponse(response)
                        );
                    } else if (response === 415) {
                        return Rx.Observable.of(
                            insertUnsuccessful("Greška", "Format odabrane datoteke nije podržan."),
                            uploadNewFileResponse(response)
                        );
                    }
                    return Rx.Observable.of(
                        insertUnsuccessful("Greška", "Došlo je do greške prilikom prijenosa. Molimo pokušajte ponovno."),
                        uploadNewFileResponse(response)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while fetching entity data', error)
                    );
                });
        });
