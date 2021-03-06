export const GET_IMAGES_BY_ENTITY_ID = "GET_IMAGES_BY_ENTITY_ID";
export const IMAGES_LOADED_BY_ENTITY_ID = "IMAGES_LOADED_BY_ENTITY_ID";
export const UPLOAD_NEW_IMAGE_BY_ENTITY_ID = "UPLOAD_NEW_IMAGE_BY_ENTITY_ID";
export const UPLOAD_NEW_IMAGE_RESPONSE = "UPLOAD_NEW_IMAGE_RESPONSE";
export const UPDATE_METADATA_IN_STORE_INFO = "UPDATE_METADATA_IN_STORE_INFO";

export const GET_DOCUMENTS_BY_ENTITY_ID = "GET_DOCUMENTS_BY_ENTITY_ID";
export const DOCUMENTS_LOADED_BY_ENTITY_ID = "DOCUMENTS_LOADED_BY_ENTITY_ID";
export const UPLOAD_NEW_DOCUMENT_BY_ENTITY_ID = "UPLOAD_NEW_DOCUMENT_BY_ENTITY_ID";
export const UPLOAD_NEW_DOCUMENT_RESPONSE = "UPLOAD_NEW_DOCUMENT_RESPONSE";


export const getImagesByEntityId = (documentType, entityFid) => ({
    type: GET_IMAGES_BY_ENTITY_ID,
    documentType,
    entityFid
});

export const imagesLoadedByEntityId = (response) => ({
    type: IMAGES_LOADED_BY_ENTITY_ID,
    response
});

export const uploadNewImageByEntityId = (documentType, file, entityFid) => ({
    type: UPLOAD_NEW_IMAGE_BY_ENTITY_ID,
    documentType,
    file,
    entityFid
});

export const uploadNewImageResponse = (response) => ({
    type: UPLOAD_NEW_IMAGE_RESPONSE,
    response
});

export const updateMetadataInStoreInfo = (entityFid) => ({
    type: UPDATE_METADATA_IN_STORE_INFO,
    entityFid
});

export const getDocumentsByEntityId  = (documentType, entityFid) => ({
    type: GET_DOCUMENTS_BY_ENTITY_ID,
    documentType,
    entityFid
});

export const documentsLoadedByEntityId = (response) => ({
    type: DOCUMENTS_LOADED_BY_ENTITY_ID,
    response
});

export const uploadNewDocumentByEntityId = (documentType, file, entityFid) => ({
    type: UPLOAD_NEW_DOCUMENT_BY_ENTITY_ID,
    documentType,
    file,
    entityFid
});

export const uploadNewDocumentResponse = (response) => ({
    type: UPLOAD_NEW_DOCUMENT_RESPONSE,
    response
});
