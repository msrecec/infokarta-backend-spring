export const GET_FILES_BY_ENTITY_ID = "GET_FILES_BY_ENTITY_ID";
export const FILES_LOADED_BY_ENTITY_ID = "FILES_LOADED_BY_ENTITY_ID";

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
