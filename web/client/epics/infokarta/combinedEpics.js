import * as deceasedEpics from './deceased';
import * as gravesEpics from './graves';
import * as fileManagementEpics from './fileManagement';
import * as detailsAndDocumentEpics from './detailsAndDocuments';
import * as lightingEpics from './lighting';

export const completeDeceasedEpic = {...deceasedEpics, ...fileManagementEpics, ...detailsAndDocumentEpics};
export const completeGravesEpic = {...gravesEpics, ...fileManagementEpics, ...detailsAndDocumentEpics};
export const completeLightingEpics = {...lightingEpics, ...detailsAndDocumentEpics};
