import * as deceasedEpics from './deceased';
import * as gravesEpics from './graves';
import * as fileManagementEpics from './fileManagement';
import * as detailsAndDocumentEpics from './detailsAndDocuments';
import * as lightingEpics from './lighting';
import * as dynamicComponentsEpics from './dynamicComponentsEpics';

export const completeDeceasedEpic = {...deceasedEpics, ...fileManagementEpics, ...detailsAndDocumentEpics, ...dynamicComponentsEpics};
export const completeGravesEpic = {...gravesEpics, ...fileManagementEpics, ...detailsAndDocumentEpics, ...dynamicComponentsEpics};
export const completeLightingEpics = {...lightingEpics, ...fileManagementEpics, ...detailsAndDocumentEpics, ...dynamicComponentsEpics};
export const deceasedAndGravesEpic = {...deceasedEpics, ...gravesEpics, ...fileManagementEpics, ...detailsAndDocumentEpics, ...dynamicComponentsEpics };
