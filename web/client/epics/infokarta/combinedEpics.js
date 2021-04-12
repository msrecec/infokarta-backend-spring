import * as deceasedEpics from './deceased';
import * as fileManagementEpics from './fileManagement';

export const deceasedAndFileManagementEpics = Object.assign(deceasedEpics, fileManagementEpics);
