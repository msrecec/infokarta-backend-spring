export const GET_FILES_BY_ENTITY_ID = "GET_FILES_BY_ENTITY_ID";
export const FILES_LOADED_BY_ENTITY_ID = "FILES_LOADED_BY_ENTITY_ID";
export const UPLOAD_NEW_FILE_BY_ENTITY_ID = "UPLOAD_NEW_FILE_BY_ENTITY_ID";
export const UPLOAD_NEW_FILE_RESPONSE = "UPLOAD_NEW_FILE_RESPONSE";

export const getFilesByEntityId = (entityName, documentType, entityFid) => ({
    type: GET_FILES_BY_ENTITY_ID,
    entityName,
    documentType,
    entityFid
});

export const filesLoadedByEntityId = (response) => ({
    type: FILES_LOADED_BY_ENTITY_ID,
    response
});

export const uploadNewFileByEntityId = (entityName, documentType, file, entityFid) => ({
    type: UPLOAD_NEW_FILE_BY_ENTITY_ID,
    entityName,
    documentType,
    file,
    entityFid
});

export const uploadNewFileResponse = (response) => ({
    type: UPLOAD_NEW_FILE_RESPONSE,
    response
});
