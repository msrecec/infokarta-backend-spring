import Rx from "rxjs";
import { get } from 'lodash';

import {
    GET_IMAGES_BY_ENTITY_ID,
    imagesLoadedByEntityId,
    UPLOAD_NEW_IMAGE_BY_ENTITY_ID,
    uploadNewImageResponse,
    UPDATE_METADATA_IN_STORE_INFO,
    updateMetadataInStoreInfo
} from "../../actions/infokarta/fileManagement";
// ../../actions/infokarta/dynamicComponents
import {
    insertSuccessful,
    insertUnsuccessful,
    ACQUIRE_CURRENT_PLUGIN_NAME
} from "../../actions/infokarta/dynamicComponents";

import fileManagementApi from "../../api/infokarta/fileManagementApi";

export const loadFileMetadataByEntityId = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        GET_IMAGES_BY_ENTITY_ID,
    )
        .switchMap(({ documentType, entityFid }) => {
            const pluginName = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(fileManagementApi.getMetaByEntityFid(pluginName, documentType, entityFid)
                .then(data => data))
                .switchMap((response) => {
                    console.log("response data parts", pluginName, documentType, entityFid);
                    console.log("response data", response.data);
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

export const handleImageUploadByEntityId = (action$, {getState = () => {}} = {}) =>
    action$.ofType(UPLOAD_NEW_IMAGE_BY_ENTITY_ID)
        .switchMap(({documentType, file, entityFid }) => {
            const pluginName = get(getState(), "dynamicComponents.activePlugin");
            return Rx.Observable.fromPromise(fileManagementApi.uploadFile(pluginName, documentType, file, entityFid)
                .then(data => data))
                .mergeMap((response) => {
                    if (response === 200) {
                        console.log("!!!!!!!!", pluginName, documentType, file, entityFid);
                        return Rx.Observable.of(
                            insertSuccessful("Prijenos uspješan", "Vaš dokument/slika je uspješno pohranjen/a u bazu podataka."),
                            uploadNewImageResponse(response),
                            updateMetadataInStoreInfo(entityFid)
                        );
                    } else if (response === 415) {
                        return Rx.Observable.of(
                            insertUnsuccessful("Greška", "Format odabrane datoteke nije podržan."),
                            uploadNewImageResponse(response),
                            updateMetadataInStoreInfo(entityFid)
                        );
                    }
                    return Rx.Observable.of(
                        insertUnsuccessful("Greška", "Došlo je do greške prilikom prijenosa. Molimo pokušajte ponovno."),
                        uploadNewImageResponse(response),
                        updateMetadataInStoreInfo(entityFid)
                    );
                })
                .catch((error) => {
                    return Rx.Observable.of(
                    /* eslint-disable no-console */
                        console.error('error while fetching entity data', error)
                    );
                });
        });

export const sendRequestUponFileInfoUpdateAndSuccessfulUpload = (action$, {getState = () => {}} = {}) =>
    action$.ofType(
        UPDATE_METADATA_IN_STORE_INFO,
        ACQUIRE_CURRENT_PLUGIN_NAME
    ).switchMap(({}) => {
        const pluginName = get(getState(), "dynamicComponents.activePlugin");
        const entityFid = get(getState(), "fileManagement.entityFid");
        console.log("!!! epiclog", pluginName, entityFid);
        return Rx.Observable.fromPromise(fileManagementApi.getMetaByEntityFid(pluginName, "slika", entityFid)
            .then(data => data))
            .mergeMap((response) => {
                return Rx.Observable.of(
                    imagesLoadedByEntityId(response.data));
            });
    });
