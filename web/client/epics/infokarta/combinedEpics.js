import * as deceasedEpics from './deceased';
import * as gravesEpics from './graves';
import * as fileManagementEpics from './fileManagement';
import * as detailsAndDocumentEpics from './detailsAndDocuments';

export const completeDeceasedEpic = {...deceasedEpics, ...fileManagementEpics, ...detailsAndDocumentEpics};
export const completeGravesEpic = {...gravesEpics, ...fileManagementEpics, ...detailsAndDocumentEpics};
